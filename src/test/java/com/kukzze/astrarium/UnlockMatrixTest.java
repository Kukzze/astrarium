package com.kukzze.astrarium;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnlockMatrixTest
{
	@Test
	public void testUnlocksExistForAllCategories()
	{
		for (UnlockMatrix.Category cat : UnlockMatrix.Category.values())
		{
			List<UnlockMatrix.UnlockEntry> unlocks = UnlockMatrix.getUnlocks(cat);
			assertNotNull(unlocks);
			assertFalse(unlocks.isEmpty());

			for (UnlockMatrix.UnlockEntry u : unlocks)
			{
				assertEquals(cat, u.getCategory());
				assertNotNull(u.getName());
				assertNotNull(u.getRequirement());
				assertNotNull(u.getImpactDescription());
			}
		}
	}
}
