package com.kukzze.astrarium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class DailiesTracker
{
	public enum Frequency
	{
		DAILY("Daily"),
		WEEKLY("Weekly");

		private final String label;

		Frequency(String label)
		{
			this.label = label;
		}

		@Override
		public String toString()
		{
			return label;
		}
	}

	@Getter
	@RequiredArgsConstructor
	public static class DailyActivity
	{
		private final String id;
		private final String name;
		private final Frequency frequency;
		private final String rewardDescription;
		private final String location;
		private final String unlockRequirement;
	}

	private static final List<DailyActivity> ACTIVITIES = new ArrayList<>();

	static
	{
		ACTIVITIES.add(new DailyActivity(
			"zaff_staves",
			"Zaff's Discounted Battlestaves",
			Frequency.DAILY,
			"15 - 120 discounted staves (Up to ~120k daily GP profit!)",
			"Varrock Center, Zaff's Staff Shop",
			"Varrock Easy -> Elite Achievement Diary"
		));

		ACTIVITIES.add(new DailyActivity(
			"nmz_herb_boxes",
			"NMZ Daily Herb Boxes",
			Frequency.DAILY,
			"15 herb boxes (~150k value in high tier herbs)",
			"Nightmare Zone Chest, Yanille",
			"Nightmare Zone reward points"
		));

		ACTIVITIES.add(new DailyActivity(
			"cromperty_essence",
			"Cromperty's Pure Essence",
			Frequency.DAILY,
			"84 - 250 free Pure Essence directly to bank",
			"East Ardougne, Wizard Cromperty",
			"Ardougne Easy -> Elite Diary"
		));

		ACTIVITIES.add(new DailyActivity(
			"geoffrey_flax",
			"Geoffrey's Flax Conversion",
			Frequency.DAILY,
			"30 - 120 flax instantly spun into bowstrings",
			"Seers' Village Flax Field",
			"Kandarin Easy -> Elite Diary"
		));

		ACTIVITIES.add(new DailyActivity(
			"bert_sand",
			"Bert's Free Sand",
			Frequency.DAILY,
			"84 free Buckets of Sand delivered to your bank",
			"Yanille, Bert's House",
			"The Hand in the Sand quest (Automatic via Ardy Elite)"
		));

		ACTIVITIES.add(new DailyActivity(
			"thirus_dynamite",
			"Thirus' Free Dynamite",
			Frequency.DAILY,
			"20 - 40 free Dynamite daily (Blast Mine / CoX)",
			"Lovakengj, Kourend",
			"Kourend & Kebos Medium -> Elite Diary"
		));

		ACTIVITIES.add(new DailyActivity(
			"robin_bones",
			"Robin's Bone Meal & Slime",
			Frequency.DAILY,
			"13 - 39 free Bone Meals & Buckets of Slime",
			"Port Phasmatys, Robin",
			"Morytania Medium -> Elite Diary"
		));

		ACTIVITIES.add(new DailyActivity(
			"tears_of_guthix",
			"Tears of Guthix (Weekly)",
			Frequency.WEEKLY,
			"Massive free XP drop directly into your lowest skill!",
			"Juna's Cave, Lumbridge Swamp Caves",
			"Tears of Guthix quest (100+ Quest Points)"
		));

		ACTIVITIES.add(new DailyActivity(
			"kingdom_miscellania",
			"Kingdom of Miscellania",
			Frequency.DAILY,
			"Maintain 100% approval & 750k+ in coffer for passive herbs & coal",
			"Miscellania Castle",
			"Throne of Miscellania & Royal Trouble quests"
		));
	}

	public static List<DailyActivity> getActivities()
	{
		return Collections.unmodifiableList(ACTIVITIES);
	}
}
