package com.kukzze.chronicle;

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
	name = "Chronicle",
	description = "The ultimate player command center: visual progression roadmap, next combat level math, live session story timeline, and custom atmospheric themes.",
	tags = {"player", "dashboard", "roadmap", "combat", "stats", "timeline", "themes", "progression", "chronicle", "kukzze"}
)
public class ChroniclePlugin extends Plugin
{
	private static final Pattern SLAYER_TASK_PATTERN = Pattern.compile("Your new task is to kill ([0-9]+) (.*)\\.");
	private static final Pattern BOSS_KC_PATTERN = Pattern.compile("Your (.*) kill count is: ([0-9,]+)\\.");
	private static final Pattern CLUE_PATTERN = Pattern.compile("You have completed ([0-9,]+) (.*) Treasure Trails\\.");

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private ChronicleConfig config;

	@Getter
	private final List<ChronicleEvent> timelineEvents = Collections.synchronizedList(new ArrayList<>());

	@Getter
	private String currentSlayerTask = null;

	private final Map<Skill, Integer> previousLevels = new EnumMap<>(Skill.class);
	private ChroniclePanel panel;
	private NavigationButton navButton;
	private boolean initialLoadComplete = false;

	@Provides
	ChronicleConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ChronicleConfig.class);
	}

	@Override
	protected void startUp()
	{
		panel = new ChroniclePanel(this, config);

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");

		navButton = NavigationButton.builder()
			.tooltip("Chronicle Hub")
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
				timelineEvents.add(0, new ChronicleEvent(
					ChronicleEvent.Type.LEVEL_UP,
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

		// Boss KC
		Matcher bossMatcher = BOSS_KC_PATTERN.matcher(clean);
		if (bossMatcher.find())
		{
			String boss = bossMatcher.group(1);
			String kc = bossMatcher.group(2);
			if (config.trackBossKills())
			{
				timelineEvents.add(0, new ChronicleEvent(
					ChronicleEvent.Type.BOSS_KILL,
					boss + " defeated!",
					"Total Kill Count: " + kc
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
			timelineEvents.add(0, new ChronicleEvent(
				ChronicleEvent.Type.MILESTONE_UNLOCKED,
				"New Slayer Task: " + amount + " " + monster,
				"Gear audit updated in Readiness tab"
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
			timelineEvents.add(0, new ChronicleEvent(
				ChronicleEvent.Type.CLUE_SCROLL,
				tier + " Clue #" + count + " Solved!",
				"Casket opened"
			));
			panel.updatePlayerData(client);
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
		if ("chronicle".equals(event.getGroup()))
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
