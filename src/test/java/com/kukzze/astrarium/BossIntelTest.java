package com.kukzze.astrarium;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BossIntelTest
{
	@Test
	public void testGuidesExist()
	{
		List<BossIntel.BossGuide> guides = BossIntel.getGuides();
		assertNotNull(guides);
		assertTrue(guides.size() >= 7);

		for (BossIntel.BossGuide g : guides)
		{
			assertNotNull(g.getBossName());
			assertNotNull(g.getDifficulty());
			assertNotNull(g.getRecommendedStats());
			assertNotNull(g.getOverheadPrayers());
			assertNotNull(g.getEssentialItems());
			assertNotNull(g.getCoreMechanics());
		}
	}
}
