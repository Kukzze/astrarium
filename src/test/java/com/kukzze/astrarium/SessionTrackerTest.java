package com.kukzze.astrarium;

import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SessionTrackerTest
{
	@Test
	public void testXpAndCounters()
	{
		SessionTracker tracker = new SessionTracker();

		// Record initial XP
		tracker.recordSkillXp(Skill.ATTACK, 10000);
		assertEquals(0, tracker.getTotalXpGained());

		// Gain XP
		tracker.recordSkillXp(Skill.ATTACK, 25000);
		assertEquals(15000, tracker.getTotalXpGained());

		// Counters
		assertEquals(0, tracker.getBossKillsCount());
		tracker.incrementBossKills();
		assertEquals(1, tracker.getBossKillsCount());

		assertEquals(0, tracker.getCluesCount());
		tracker.incrementClues();
		assertEquals(1, tracker.getCluesCount());

		assertEquals(0, tracker.getQuestsCount());
		tracker.incrementQuests();
		assertEquals(1, tracker.getQuestsCount());

		assertEquals(0, tracker.getCollectionLogCount());
		tracker.incrementCollectionLog();
		assertEquals(1, tracker.getCollectionLogCount());

		assertNotNull(tracker.getSessionDurationString());
	}
}
