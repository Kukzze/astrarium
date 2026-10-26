package com.kukzze.chronicle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CombatLevelMathTest
{
	@Test
	public void testFreshAccountLevel3()
	{
		// Fresh level 3 account: 1 att, 1 str, 1 def, 10 hp, 1 pray, 1 range, 1 mage
		CombatLevelMath math = new CombatLevelMath(1, 1, 1, 10, 1, 1, 1);
		assertEquals(3, math.getCombatLevel());
		assertTrue(math.getPreciseCombatLevel() >= 3.0 && math.getPreciseCombatLevel() < 4.0);
		assertTrue(math.getProgressToNext() >= 0.0 && math.getProgressToNext() < 1.0);

		// Must need a positive number of levels for next combat
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
		// Maxed account cannot gain more levels
		assertEquals(-1, math.getAttackNeeded());
		assertEquals(-1, math.getStrengthNeeded());
		assertEquals(-1, math.getDefenceNeeded());
		assertEquals(-1, math.getHitpointsNeeded());
	}

	@Test
	public void testMidLevelCombatCalculations()
	{
		// 75 att, 75 str, 70 def, 80 hp, 43 pray, 70 range, 70 mage
		CombatLevelMath math = new CombatLevelMath(75, 75, 70, 80, 43, 70, 70);
		// base = 0.25 * (70 + 80 + 21) = 0.25 * 171 = 42.75
		// melee = 0.325 * 150 = 48.75
		// combat = 42.75 + 48.75 = 91.5 -> level 91
		assertEquals(91, math.getCombatLevel());
		assertEquals(91.5, math.getPreciseCombatLevel(), 0.001);
		assertEquals(0.5, math.getProgressToNext(), 0.001);

		// To reach 92: needs 0.5 combat level
		// melee gives 0.325 per att/str. 2 levels = 0.65 -> enough!
		assertEquals(2, math.getAttackNeeded());
		assertEquals(2, math.getStrengthNeeded());
		// def/hp gives 0.25 per level. 2 levels = 0.50 -> 42.75 + 0.50 + 48.75 = 92.00!
		assertEquals(2, math.getDefenceNeeded());
		assertEquals(2, math.getHitpointsNeeded());
	}
}
