package com.kukzze.astrarium;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GearProgressionTest
{
	@Test
	public void testUpgradesExistForAllStyles()
	{
		for (GearProgression.Style style : GearProgression.Style.values())
		{
			List<GearProgression.GearUpgrade> upgrades = GearProgression.getUpgrades(style);
			assertNotNull(upgrades);
			assertFalse(upgrades.isEmpty());
			assertTrue(upgrades.size() >= 5);

			for (GearProgression.GearUpgrade up : upgrades)
			{
				assertEquals(style, up.getStyle());
				assertTrue(up.getTier() >= 1 && up.getTier() <= 5);
				assertNotNull(up.getName());
				assertNotNull(up.getSlot());
				assertNotNull(up.getBonusDescription());
				assertNotNull(up.getRequirementOrSource());
			}
		}
	}
}
