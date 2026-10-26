package com.kukzze.chronicle;

import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

@Getter
@RequiredArgsConstructor
public class Milestone
{
	public enum Tier
	{
		EARLY("Early Game"),
		MID("Mid Game"),
		LATE("Late Game"),
		ENDGAME("Endgame");

		private final String label;

		Tier(String label)
		{
			this.label = label;
		}

		@Override
		public String toString()
		{
			return label;
		}
	}

	private final String title;
	private final String unlockDescription;
	private final Tier tier;
	private final Map<Skill, Integer> skillRequirements;

	public boolean isCompleted(Map<Skill, Integer> currentLevels)
	{
		for (Map.Entry<Skill, Integer> req : skillRequirements.entrySet())
		{
			int current = currentLevels.getOrDefault(req.getKey(), 1);
			if (current < req.getValue())
			{
				return false;
			}
		}
		return true;
	}

	public double getCompletionPercentage(Map<Skill, Integer> currentLevels)
	{
		if (skillRequirements.isEmpty())
		{
			return 100.0;
		}

		int totalMet = 0;
		for (Map.Entry<Skill, Integer> req : skillRequirements.entrySet())
		{
			int current = currentLevels.getOrDefault(req.getKey(), 1);
			if (current >= req.getValue())
			{
				totalMet++;
			}
		}

		return ((double) totalMet / skillRequirements.size()) * 100.0;
	}
}
