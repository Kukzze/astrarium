package com.kukzze.astrarium;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("astrarium")
public interface AstrariumConfig extends Config
{
	@ConfigSection(
		name = "Theme & Visuals",
		description = "Atmospheric visual styles inspired by Gielinor lore",
		position = 0
	)
	String themeSection = "themeSection";

	@ConfigSection(
		name = "Progression & Combat Math",
		description = "Live combat level formula and next level radar",
		position = 1
	)
	String milestoneSection = "milestoneSection";

	@ConfigSection(
		name = "Quest & Leveling Guides",
		description = "Optimal route suggestions and training methods",
		position = 2
	)
	String guideSection = "guideSection";

	@ConfigSection(
		name = "Session Timeline",
		description = "Adventure log tracking achievements and drops",
		position = 3
	)
	String sessionSection = "sessionSection";

	@ConfigSection(
		name = "Discord Integration",
		description = "Share achievements to your private Discord webhook",
		position = 4,
		closedByDefault = true
	)
	String discordSection = "discordSection";

	@ConfigItem(
		keyName = "theme",
		name = "Atmospheric Theme",
		description = "Select the visual color scheme for Astrarium.",
		position = 0,
		section = themeSection
	)
	default AstrariumTheme theme()
	{
		return AstrariumTheme.SUNFIRE_GOLD;
	}

	@ConfigItem(
		keyName = "showCombatMath",
		name = "Show Combat Level Math",
		description = "Displays the exact skills and levels needed for your next combat level.",
		position = 0,
		section = milestoneSection
	)
	default boolean showCombatMath()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showOptimalQuests",
		name = "Show Optimal Quest Guide",
		description = "Recommends high-impact quests and tracks optimal quest progression.",
		position = 0,
		section = guideSection
	)
	default boolean showOptimalQuests()
	{
		return true;
	}

	@ConfigItem(
		keyName = "trackLevelUps",
		name = "Track Level Ups",
		description = "Automatically logs skill level-ups into the session timeline.",
		position = 0,
		section = sessionSection
	)
	default boolean trackLevelUps()
	{
		return true;
	}

	@ConfigItem(
		keyName = "trackBossKills",
		name = "Track Boss Kills & Raids",
		description = "Records boss killcounts, completions, and personal bests.",
		position = 1,
		section = sessionSection
	)
	default boolean trackBossKills()
	{
		return true;
	}

	@ConfigItem(
		keyName = "trackClues",
		name = "Track Clue Scrolls",
		description = "Records completed clue scrolls and casket opens.",
		position = 2,
		section = sessionSection
	)
	default boolean trackClues()
	{
		return true;
	}

	@ConfigItem(
		keyName = "trackQuests",
		name = "Track Quests & Tasks",
		description = "Records completed quests and combat achievements.",
		position = 3,
		section = sessionSection
	)
	default boolean trackQuests()
	{
		return true;
	}

	@ConfigItem(
		keyName = "trackCollectionLog",
		name = "Track Collection Log",
		description = "Records new collection log additions.",
		position = 4,
		section = sessionSection
	)
	default boolean trackCollectionLog()
	{
		return true;
	}

	@ConfigItem(
		keyName = "discordWebhook",
		name = "Discord Webhook URL",
		description = "Optional Discord webhook for exporting session summary cards.",
		position = 0,
		section = discordSection
	)
	default String discordWebhook()
	{
		return "";
	}
}
