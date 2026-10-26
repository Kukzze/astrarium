package com.kukzze.chronicle;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MilestoneTest
{
	@Test
	public void testMilestoneCompletion()
	{
		Milestone milestone = new Milestone(
			"Piety",
			"Piety prayer",
			Milestone.Tier.MID,
			ImmutableMap.of(Skill.PRAYER, 70, Skill.DEFENCE, 70)
		);

		Map<Skill, Integer> lowStats = ImmutableMap.of(Skill.PRAYER, 43, Skill.DEFENCE, 60);
		assertFalse(milestone.isCompleted(lowStats));
		assertEquals(0.0, milestone.getCompletionPercentage(lowStats), 0.01);

		Map<Skill, Integer> partialStats = ImmutableMap.of(Skill.PRAYER, 70, Skill.DEFENCE, 60);
		assertFalse(milestone.isCompleted(partialStats));
		assertEquals(50.0, milestone.getCompletionPercentage(partialStats), 0.01);

		Map<Skill, Integer> metStats = ImmutableMap.of(Skill.PRAYER, 77, Skill.DEFENCE, 70);
		assertTrue(milestone.isCompleted(metStats));
		assertEquals(100.0, milestone.getCompletionPercentage(metStats), 0.01);
	}

	@Test
	public void testRepositoryNotEmpty()
	{
		List<Milestone> milestones = MilestoneRepository.getMilestones();
		assertNotNull(milestones);
		assertFalse(milestones.isEmpty());
		assertTrue(milestones.size() >= 8);

		for (Milestone m : milestones)
		{
			assertNotNull(m.getTitle());
			assertNotNull(m.getTier());
			assertNotNull(m.getUnlockDescription());
			assertFalse(m.getSkillRequirements().isEmpty());
		}
	}
}
