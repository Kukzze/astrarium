package com.kukzze.astrarium;

import java.time.Duration;
import java.time.Instant;
import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;
import net.runelite.api.Skill;

@Getter
public class SessionTracker
{
	private final Instant startTime = Instant.now();
	private final Map<Skill, Long> initialXp = new EnumMap<>(Skill.class);
	private final Map<Skill, Long> currentXp = new EnumMap<>(Skill.class);

	private int bossKillsCount = 0;
	private int cluesCount = 0;
	private int questsCount = 0;
	private int collectionLogCount = 0;
	private long totalGpValue = 0;

	public void recordSkillXp(Skill skill, long xp)
	{
		if (!initialXp.containsKey(skill))
		{
			initialXp.put(skill, xp);
		}
		currentXp.put(skill, xp);
	}

	public long getTotalXpGained()
	{
		long gained = 0;
		for (Map.Entry<Skill, Long> entry : currentXp.entrySet())
		{
			Long init = initialXp.get(entry.getKey());
			if (init != null && entry.getValue() > init)
			{
				gained += (entry.getValue() - init);
			}
		}
		return gained;
	}

	public long getXpPerHour()
	{
		long seconds = Math.max(1, Duration.between(startTime, Instant.now()).getSeconds());
		return (long) ((double) getTotalXpGained() / seconds * 3600.0);
	}

	public String getSessionDurationString()
	{
		Duration dur = Duration.between(startTime, Instant.now());
		long hours = dur.toHours();
		long minutes = dur.toMinutesPart();
		return String.format("%02dh %02dm", hours, minutes);
	}

	public void incrementBossKills()
	{
		bossKillsCount++;
	}

	public void incrementClues()
	{
		cluesCount++;
	}

	public void incrementQuests()
	{
		questsCount++;
	}

	public void incrementCollectionLog()
	{
		collectionLogCount++;
	}

	public void addGpValue(long value)
	{
		if (value > 0)
		{
			totalGpValue += value;
		}
	}
}
