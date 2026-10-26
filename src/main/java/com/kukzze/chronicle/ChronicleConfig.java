package com.kukzze.chronicle;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup("chronicle")
public interface ChronicleConfig extends Config
{
	@ConfigSection(
		name = "Theme & Appearance",
		description = "Atmospheric visual styles and colors",
		position = 0
	)
	String themeSection = "themeSection";

	@ConfigSection(
		name = "Milestone Radar",
		description = "Progression roadmap and combat calculation",
		position = 1
	)
	String milestoneSection = "milestoneSection";

	@ConfigSection(
		name = "Session Story",
		description = "Timeline tracking of your achievements",
		position = 2
	)
	String sessionSection = "sessionSection";

	@ConfigSection(
		name = "Discord Integration",
		description = "Share session cards and achievements to Discord",
		position = 3,
		closedByDefault = true
	)
	String discordSection = "discordSection";

	@ConfigItem(
		keyName = "theme",
		name = "Atmospheric Theme",
		description = "Select the visual color scheme for Chronicle.",
		position = 0,
		section = themeSection
	)
	default ChronicleTheme theme()
	{
		return ChronicleTheme.SUNFIRE_GOLD;
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
		keyName = "trackLevelUps",
		name = "Track Level Ups",
		description = "Automatically records skill level-ups into the session timeline.",
		position = 0,
		section = sessionSection
	)
	default boolean trackLevelUps()
	{
		return true;
	}

	@ConfigItem(
		keyName = "trackBossKills",
		name = "Track Boss Kills & PBs",
		description = "Records boss personal bests and notable killcounts.",
		position = 1,
		section = sessionSection
	)
	default boolean trackBossKills()
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
