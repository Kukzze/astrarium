package com.kukzze.astrarium;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FarmingPatchesTest
{
	@Test
	public void testNineHerbPatches()
	{
		List<FarmingPatches.HerbPatch> patches = FarmingPatches.getPatches();
		assertNotNull(patches);
		assertEquals(9, patches.size());

		int diseaseFreeCount = 0;
		for (FarmingPatches.HerbPatch p : patches)
		{
			assertNotNull(p.getLocation());
			assertNotNull(p.getTeleportMethod());
			assertNotNull(p.getUnlockRequirement());
			assertNotNull(p.getDiaryBonus());
			if (p.isDiseaseFree()) diseaseFreeCount++;
		}

		// Weiss, Troll Stronghold, and Hosidius are disease-free
		assertEquals(3, diseaseFreeCount);
	}
}
