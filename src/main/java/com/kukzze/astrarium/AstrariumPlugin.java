package com.kukzze.astrarium;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

@Slf4j
@Singleton
@PluginDescriptor(
	name = "Astrarium",
	description = "Your personal Gielinor astrolabe: live combat level math, milestone progression radar, optimal quest & leveling guide, and gear readiness audit.",
	tags = {"combat", "stats", "progression", "roadmap", "timeline", "dashboard", "quests", "leveling", "guide", "astrarium", "kukzze"}
)
public class AstrariumPlugin extends Plugin
{
	private static final Pattern SLAYER_TASK_PATTERN = Pattern.compile("(?:Your new task is to kill|You're assigned to kill) ([0-9]+) (.*)[.!]");
	private static final Pattern BOSS_KC_PATTERN = Pattern.compile("Your (?:completed )?(.*?) (?:kill count|completion count|supply crate count|count) is:? ([0-9,]+)\\.");
	private static final Pattern CLUE_PATTERN = Pattern.compile("You have completed ([0-9,]+) (.*) Treasure Trails?\\.");
	private static final Pattern COLL_LOG_PATTERN = Pattern.compile("New item added to your collection log: (.*)");
	private static final Pattern QUEST_PATTERN = Pattern.compile("(?:Congratulations, you have completed|Quest complete:) (.*?)[.!]");
	private static final Pattern COMBAT_ACHIEVEMENT_PATTERN = Pattern.compile("Congratulations, you've completed an? (?:Easy|Medium|Hard|Elite|Master|Grandmaster) combat task: (.*)\\.");

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private AstrariumConfig config;

	@Getter
	private final List<AstrariumEvent> timelineEvents = Collections.synchronizedList(new ArrayList<>());

	@Getter
	private String currentSlayerTask = null;

	private final Map<Skill, Integer> previousLevels = new EnumMap<>(Skill.class);
	private AstrariumPanel panel;
	private NavigationButton navButton;
	private boolean initialLoadComplete = false;

	@Provides
	AstrariumConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AstrariumConfig.class);
	}

	@Override
	protected void startUp()
	{
		panel = new AstrariumPanel(this, config);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");

		navButton = NavigationButton.builder()
			.tooltip("Astrarium Hub")
			.icon(icon)
			.priority(4)
			.panel(panel)
			.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown()
	{
		clientToolbar.removeNavigation(navButton);
		timelineEvents.clear();
		previousLevels.clear();
		initialLoadComplete = false;
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			clientThread.invoke(() ->
			{
				recordInitialLevels();
				panel.updatePlayerData(client);
			});
		}
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		Skill skill = event.getSkill();
		int newLevel = event.getLevel();

		Integer prevLevel = previousLevels.get(skill);
		if (prevLevel != null && newLevel > prevLevel)
		{
			if (config.trackLevelUps())
			{
				timelineEvents.add(0, new AstrariumEvent(
					AstrariumEvent.Type.LEVEL_UP,
					skill.getName() + " reached Level " + newLevel + "!",
					"XP: " + String.format("%,d", event.getXp())
				));
			}
			previousLevels.put(skill, newLevel);
			panel.updatePlayerData(client);
		}
		else if (prevLevel == null)
		{
			previousLevels.put(skill, newLevel);
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() != ChatMessageType.GAMEMESSAGE && event.getType() != ChatMessageType.SPAM)
		{
			return;
		}

		String clean = Text.removeTags(event.getMessage());

		// Boss KC & Raids
		Matcher bossMatcher = BOSS_KC_PATTERN.matcher(clean);
		if (bossMatcher.find())
		{
			String boss = bossMatcher.group(1).trim();
			String count = bossMatcher.group(2);
			if (config.trackBossKills())
			{
				timelineEvents.add(0, new AstrariumEvent(
					AstrariumEvent.Type.BOSS_KILL,
					boss + " completed!",
					"Count: " + count
				));
				panel.updatePlayerData(client);
			}
			return;
		}

		// Collection Log
		Matcher collMatcher = COLL_LOG_PATTERN.matcher(clean);
		if (collMatcher.find())
		{
			String item = collMatcher.group(1);
			if (config.trackCollectionLog())
			{
				timelineEvents.add(0, new AstrariumEvent(
					AstrariumEvent.Type.COLLECTION_LOG,
					"Collection Log: " + item,
					"Unlocked new unique drop!"
				));
				panel.updatePlayerData(client);
			}
			return;
		}

		// Slayer Task
		Matcher slayerMatcher = SLAYER_TASK_PATTERN.matcher(clean);
		if (slayerMatcher.find())
		{
			String amount = slayerMatcher.group(1);
			String monster = slayerMatcher.group(2);
			currentSlayerTask = monster;
			timelineEvents.add(0, new AstrariumEvent(
				AstrariumEvent.Type.MILESTONE_UNLOCKED,
				"New Slayer Task: " + amount + " " + monster,
				"Gear audit updated in Gear tab"
			));
			panel.updatePlayerData(client);
			return;
		}

		// Clue completions
		Matcher clueMatcher = CLUE_PATTERN.matcher(clean);
		if (clueMatcher.find())
		{
			String count = clueMatcher.group(1);
			String tier = clueMatcher.group(2);
			if (config.trackClues())
			{
				timelineEvents.add(0, new AstrariumEvent(
					AstrariumEvent.Type.CLUE_SCROLL,
					tier + " Clue #" + count + " Solved!",
					"Casket opened"
				));
				panel.updatePlayerData(client);
			}
			return;
		}

		// Quest completions
		Matcher questMatcher = QUEST_PATTERN.matcher(clean);
		if (questMatcher.find())
		{
			String questName = questMatcher.group(1).trim();
			if (config.trackQuests())
			{
				timelineEvents.add(0, new AstrariumEvent(
					AstrariumEvent.Type.QUEST_COMPLETE,
					questName + " Completed!",
					"Quest points awarded"
				));
				panel.updatePlayerData(client);
			}
			return;
		}

		// Combat Achievements
		Matcher caMatcher = COMBAT_ACHIEVEMENT_PATTERN.matcher(clean);
		if (caMatcher.find())
		{
			String taskName = caMatcher.group(1).trim();
			if (config.trackQuests())
			{
				timelineEvents.add(0, new AstrariumEvent(
					AstrariumEvent.Type.COMBAT_ACHIEVEMENT,
					"Task: " + taskName,
					"Combat achievement points earned"
				));
				panel.updatePlayerData(client);
			}
		}
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (!initialLoadComplete && client.getGameState() == GameState.LOGGED_IN)
		{
			recordInitialLevels();
			panel.updatePlayerData(client);
			initialLoadComplete = true;
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if ("astrarium".equals(event.getGroup()))
		{
			clientThread.invoke(() -> panel.updatePlayerData(client));
		}
	}

	private void recordInitialLevels()
	{
		for (Skill skill : Skill.values())
		{
			previousLevels.put(skill, client.getRealSkillLevel(skill));
		}
	}
}
