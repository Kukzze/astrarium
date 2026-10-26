package com.kukzze.astrarium;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DailiesTrackerTest
{
	@Test
	public void testActivitiesListValid()
	{
		List<DailiesTracker.DailyActivity> activities = DailiesTracker.getActivities();
		assertNotNull(activities);
		assertTrue(activities.size() >= 8);

		for (DailiesTracker.DailyActivity act : activities)
		{
			assertNotNull(act.getId());
			assertNotNull(act.getName());
			assertNotNull(act.getFrequency());
			assertNotNull(act.getRewardDescription());
			assertNotNull(act.getLocation());
			assertNotNull(act.getUnlockRequirement());
		}
	}
}
