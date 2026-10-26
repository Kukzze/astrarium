package com.kukzze.chronicle;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ChronicleEvent
{
	@Getter
	public enum Type
	{
		LEVEL_UP("Level Up", "\uD83C\uDF1F"),
		BOSS_KILL("Boss PB/Kill", "\u2694\uFE0F"),
		CLUE_SCROLL("Clue Scroll", "\uD83D\uDCDC"),
		QUEST_COMPLETE("Quest Complete", "\uD83C\uDFC6"),
		COMBAT_ACHIEVEMENT("Combat Achievement", "\u2B50"),
		MILESTONE_UNLOCKED("Milestone Reached", "\uD83D\uDD25");

		private final String label;
		private final String emoji;

		Type(String label, String emoji)
		{
			this.label = label;
			this.emoji = emoji;
		}

		@Override
		public String toString()
		{
			return label;
		}
	}

	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

	private final String timestamp;
	private final Type type;
	private final String headline;
	private final String subtext;

	public ChronicleEvent(Type type, String headline, String subtext)
	{
		this.timestamp = LocalTime.now().format(TIME_FORMAT);
		this.type = type;
		this.headline = headline;
		this.subtext = subtext;
	}
}
