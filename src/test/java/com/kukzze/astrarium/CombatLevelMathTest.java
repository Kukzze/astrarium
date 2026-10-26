package com.kukzze.astrarium;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombatLevelMathTest
{
	@Test
	public void testFreshAccountLevel3()
	{
		CombatLevelMath math = new CombatLevelMath(1, 1, 1, 10, 1, 1, 1);
		assertEquals(3, math.getCombatLevel());
		assertTrue(math.getPreciseCombatLevel() >= 3.0 && math.getPreciseCombatLevel() < 4.0);
		assertTrue(math.getProgressToNext() >= 0.0 && math.getProgressToNext() < 1.0);

		assertTrue(math.getAttackNeeded() > 0);
		assertTrue(math.getStrengthNeeded() > 0);
		assertTrue(math.getDefenceNeeded() > 0);
		assertTrue(math.getHitpointsNeeded() > 0);
	}

	@Test
	public void testMaxedCombatLevel126()
	{
		CombatLevelMath math = new CombatLevelMath(99, 99, 99, 99, 99, 99, 99);
		assertEquals(126, math.getCombatLevel());
		assertEquals(-1, math.getAttackNeeded());
		assertEquals(-1, math.getStrengthNeeded());
		assertEquals(-1, math.getDefenceNeeded());
		assertEquals(-1, math.getHitpointsNeeded());
		assertEquals(1.0, math.getProgressToNext(), 0.001);
	}

	@Test
	public void testMidLevelCombatCalculations()
	{
		CombatLevelMath math = new CombatLevelMath(75, 75, 70, 80, 43, 70, 70);
		assertEquals(91, math.getCombatLevel());
		assertEquals(91.5, math.getPreciseCombatLevel(), 0.001);
		assertEquals(0.5, math.getProgressToNext(), 0.001);

		assertEquals(2, math.getAttackNeeded());
		assertEquals(2, math.getStrengthNeeded());
		assertEquals(2, math.getDefenceNeeded());
		assertEquals(2, math.getHitpointsNeeded());
	}
}
