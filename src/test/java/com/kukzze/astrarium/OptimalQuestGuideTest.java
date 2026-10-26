package com.kukzze.astrarium;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimalQuestGuideTest
{
	@Test
	public void testOptimalQuestsListNotEmpty()
	{
		List<OptimalQuestGuide.QuestEntry> quests = OptimalQuestGuide.getOptimalQuests();
		assertNotNull(quests);
		assertTrue(quests.size() >= 15);

		for (OptimalQuestGuide.QuestEntry q : quests)
		{
			assertNotNull(q.getQuest());
			assertNotNull(q.getTitle());
			assertNotNull(q.getRewardHighlight());
			assertTrue(q.getQuestPoints() > 0);
		}
	}

	@Test
	public void testSkillRequirementsCheck()
	{
		OptimalQuestGuide.QuestEntry waterfall = OptimalQuestGuide.getOptimalQuests().get(0);
		// Waterfall has no skill requirements
		assertTrue(waterfall.meetsSkillRequirements(ImmutableMap.of()));

		// Dragon Slayer II has requirements
		OptimalQuestGuide.QuestEntry ds2 = OptimalQuestGuide.getOptimalQuests().stream()
			.filter(q -> q.getTitle().equals("Dragon Slayer II"))
			.findFirst()
			.orElse(null);

		assertNotNull(ds2);
		Map<Skill, Integer> lowStats = ImmutableMap.of(Skill.MAGIC, 50, Skill.SMITHING, 50);
		assertFalse(ds2.meetsSkillRequirements(lowStats));
		assertTrue(ds2.getMissingSkillsString(lowStats).contains("Magic"));

		Map<Skill, Integer> highStats = ImmutableMap.<Skill, Integer>builder()
			.put(Skill.MAGIC, 75)
			.put(Skill.SMITHING, 70)
			.put(Skill.MINING, 68)
			.put(Skill.CRAFTING, 62)
			.put(Skill.AGILITY, 60)
			.put(Skill.THIEVING, 60)
			.build();
		assertTrue(ds2.meetsSkillRequirements(highStats));
		assertEquals("Ready!", ds2.getMissingSkillsString(highStats));
	}
}
