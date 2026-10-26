package com.kukzze.astrarium;

import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OptimalLevelingGuideTest
{
	@Test
	public void testAgilityTrainingBrackets()
	{
		// Level 1 Agility -> Gnome Stronghold
		OptimalLevelingGuide.TrainingMethod methodLvl1 = OptimalLevelingGuide.getOptimalMethod(Skill.AGILITY, 1);
		assertNotNull(methodLvl1);
		assertEquals("Gnome Stronghold Course", methodLvl1.getName());

		// Level 45 Agility -> Canifis
		OptimalLevelingGuide.TrainingMethod methodLvl45 = OptimalLevelingGuide.getOptimalMethod(Skill.AGILITY, 45);
		assertNotNull(methodLvl45);
		assertEquals("Canifis Rooftop Course", methodLvl45.getName());

		// Level 95 Agility -> Ardougne
		OptimalLevelingGuide.TrainingMethod methodLvl95 = OptimalLevelingGuide.getOptimalMethod(Skill.AGILITY, 95);
		assertNotNull(methodLvl95);
		assertEquals("Ardougne Rooftop Course", methodLvl95.getName());
	}

	@Test
	public void testNextMethodPreview()
	{
		// At level 50 Agility, current is Canifis (40-60), next is Seers (60-80)
		OptimalLevelingGuide.TrainingMethod next = OptimalLevelingGuide.getNextMethod(Skill.AGILITY, 50);
		assertNotNull(next);
		assertTrue(next.getName().contains("Seers"));
		assertEquals(60, next.getMinLevel());
	}

	@Test
	public void testMeleeQuestsRecommendedAtLevel1()
	{
		OptimalLevelingGuide.TrainingMethod method = OptimalLevelingGuide.getOptimalMethod(Skill.ATTACK, 1);
		assertNotNull(method);
		assertTrue(method.getName().contains("Waterfall Quest"));
	}
}
