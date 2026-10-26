package com.kukzze.astrarium;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WildernessSurvivalTest
{
	@Test
	public void testTipsListValid()
	{
		List<WildernessSurvival.SurvivalTip> tips = WildernessSurvival.getTips();
		assertNotNull(tips);
		assertTrue(tips.size() >= 5);

		for (WildernessSurvival.SurvivalTip t : tips)
		{
			assertNotNull(t.getTitle());
			assertNotNull(t.getRuleLevel());
			assertNotNull(t.getDescription());
		}
	}
}
