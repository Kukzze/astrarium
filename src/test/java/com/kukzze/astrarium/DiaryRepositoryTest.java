package com.kukzze.astrarium;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiaryRepositoryTest
{
	@Test
	public void testDiariesNotEmpty()
	{
		List<DiaryRepository.DiaryEntry> diaries = DiaryRepository.getDiaries();
		assertNotNull(diaries);
		assertTrue(diaries.size() >= 10);

		for (DiaryRepository.DiaryEntry d : diaries)
		{
			assertNotNull(d.getRegion());
			assertNotNull(d.getTier());
			assertNotNull(d.getRewardHighlights());
			assertNotNull(d.getSkillRequirements());
		}
	}

	@Test
	public void testRequirementsCheck()
	{
		DiaryRepository.DiaryEntry ardyEasy = DiaryRepository.getDiaries().get(0);
		// Ardy Easy requires 5 Thieving
		Map<Skill, Integer> lowStats = ImmutableMap.of(Skill.THIEVING, 1);
		assertFalse(ardyEasy.meetsRequirements(lowStats));
		assertTrue(ardyEasy.getMissingSkillsString(lowStats).contains("Thieving"));

		Map<Skill, Integer> metStats = ImmutableMap.of(Skill.THIEVING, 10);
		assertTrue(ardyEasy.meetsRequirements(metStats));
		assertEquals("Ready to Complete!", ardyEasy.getMissingSkillsString(metStats));
	}
}
