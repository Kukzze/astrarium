package com.kukzze.astrarium;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

public class DiaryRepository
{
	public enum DiaryTier
	{
		EASY("Easy"),
		MEDIUM("Medium"),
		HARD("Hard"),
		ELITE("Elite");

		private final String label;

		DiaryTier(String label)
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
	public static class DiaryEntry
	{
		private final String region;
		private final DiaryTier tier;
		private final String rewardHighlights;
		private final Map<Skill, Integer> skillRequirements;

		public boolean meetsRequirements(Map<Skill, Integer> currentLevels)
		{
			for (Map.Entry<Skill, Integer> req : skillRequirements.entrySet())
			{
				int level = currentLevels.getOrDefault(req.getKey(), 1);
				if (level < req.getValue())
				{
					return false;
				}
			}
			return true;
		}

		public String getMissingSkillsString(Map<Skill, Integer> currentLevels)
		{
			List<String> missing = new ArrayList<>();
			for (Map.Entry<Skill, Integer> req : skillRequirements.entrySet())
			{
				int current = currentLevels.getOrDefault(req.getKey(), 1);
				if (current < req.getValue())
				{
					missing.add(req.getValue() + " " + req.getKey().getName());
				}
			}
			return missing.isEmpty() ? "Ready to Complete!" : "Needs: " + String.join(", ", missing);
		}
	}

	private static final List<DiaryEntry> DIARIES = new ArrayList<>();

	static
	{
		// Ardougne
		DIARIES.add(new DiaryEntry(
			"Ardougne",
			DiaryTier.EASY,
			"Ardougne Cloak 1 (Unlimited Kandarin Monastery teleports)",
			ImmutableMap.of(Skill.THIEVING, 5)
		));
		DIARIES.add(new DiaryEntry(
			"Ardougne",
			DiaryTier.MEDIUM,
			"+10% pickpocketing success rate & Ardougne Cloak 2 (3 teleports to farming patch)",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 39)
				.put(Skill.FARMING, 31)
				.put(Skill.FIREMAKING, 50)
				.put(Skill.MAGIC, 51)
				.put(Skill.STRENGTH, 38)
				.put(Skill.THIEVING, 38)
				.build()
		));
		DIARIES.add(new DiaryEntry(
			"Ardougne",
			DiaryTier.HARD,
			"Unlimited farm patch teleports & 10% more Marks of Grace at Ardy Rooftop",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 56)
				.put(Skill.CONSTRUCTION, 50)
				.put(Skill.COOKING, 65)
				.put(Skill.CRAFTING, 50)
				.put(Skill.FARMING, 70)
				.put(Skill.FIREMAKING, 50)
				.put(Skill.FLETCHING, 50)
				.put(Skill.HUNTER, 59)
				.put(Skill.MAGIC, 66)
				.put(Skill.MINING, 52)
				.put(Skill.PRAYER, 42)
				.put(Skill.RUNECRAFT, 65)
				.put(Skill.SMITHING, 68)
				.put(Skill.STRENGTH, 50)
				.put(Skill.THIEVING, 72)
				.put(Skill.WOODCUTTING, 50)
				.build()
		));

		// Lumbridge & Draynor
		DIARIES.add(new DiaryEntry(
			"Lumbridge & Draynor",
			DiaryTier.EASY,
			"Explorer's Ring 1 (Restore run energy 50% 2x daily)",
			ImmutableMap.of(Skill.WOODCUTTING, 15, Skill.MINING, 15, Skill.FISHING, 15)
		));
		DIARIES.add(new DiaryEntry(
			"Lumbridge & Draynor",
			DiaryTier.MEDIUM,
			"Explorer's Ring 2 (3 daily cabbage patch teleports)",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 20)
				.put(Skill.CRAFTING, 38)
				.put(Skill.FISHING, 30)
				.put(Skill.HUNTER, 50)
				.put(Skill.MAGIC, 31)
				.put(Skill.STRENGTH, 30)
				.put(Skill.THIEVING, 36)
				.put(Skill.WOODCUTTING, 36)
				.build()
		));
		DIARIES.add(new DiaryEntry(
			"Lumbridge & Draynor",
			DiaryTier.HARD,
			"Explorer's Ring 3 (30 free High Alchemy casts daily & infinite run energy restores)",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 46)
				.put(Skill.CRAFTING, 70)
				.put(Skill.FIREMAKING, 65)
				.put(Skill.FARMING, 63)
				.put(Skill.MAGIC, 60)
				.put(Skill.MINING, 70)
				.put(Skill.PRAYER, 52)
				.put(Skill.RUNECRAFT, 59)
				.put(Skill.SMITHING, 60)
				.put(Skill.WOODCUTTING, 57)
				.build()
		));

		// Morytania
		DIARIES.add(new DiaryEntry(
			"Morytania",
			DiaryTier.MEDIUM,
			"Ghostspeak Amulet passive & Burgh de Rott shortcut",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 42)
				.put(Skill.COOKING, 40)
				.put(Skill.FISHING, 50)
				.put(Skill.HERBLORE, 22)
				.put(Skill.HUNTER, 29)
				.put(Skill.SLAYER, 42)
				.put(Skill.WOODCUTTING, 45)
				.build()
		));
		DIARIES.add(new DiaryEntry(
			"Morytania",
			DiaryTier.HARD,
			"+50% more Runes from Barrows chest & Bonecrusher in inventory!",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 71)
				.put(Skill.CONSTRUCTION, 50)
				.put(Skill.DEFENCE, 70)
				.put(Skill.FARMING, 53)
				.put(Skill.FIREMAKING, 50)
				.put(Skill.MAGIC, 66)
				.put(Skill.MINING, 55)
				.put(Skill.PRAYER, 70)
				.put(Skill.SLAYER, 58)
				.put(Skill.SMITHING, 50)
				.put(Skill.THIEVING, 53)
				.put(Skill.WOODCUTTING, 50)
				.build()
		));

		// Kandarin
		DIARIES.add(new DiaryEntry(
			"Kandarin",
			DiaryTier.MEDIUM,
			"+10% extra marks at Seers Rooftop & 33% faster spinning",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 36)
				.put(Skill.COOKING, 43)
				.put(Skill.FARMING, 26)
				.put(Skill.FISHING, 46)
				.put(Skill.FLETCHING, 50)
				.put(Skill.HERBLORE, 48)
				.put(Skill.MAGIC, 45)
				.put(Skill.MINING, 30)
				.put(Skill.STRENGTH, 22)
				.build()
		));
		DIARIES.add(new DiaryEntry(
			"Kandarin",
			DiaryTier.HARD,
			"Camelot teleport redirected directly to Seers' Bank (+15k Agility XP/hr!) & 10% extra logs",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 60)
				.put(Skill.CRAFTING, 10)
				.put(Skill.DEFENCE, 70)
				.put(Skill.FIREMAKING, 65)
				.put(Skill.FISHING, 70)
				.put(Skill.FLETCHING, 70)
				.put(Skill.MAGIC, 56)
				.put(Skill.PRAYER, 70)
				.put(Skill.SMITHING, 75)
				.put(Skill.STRENGTH, 50)
				.put(Skill.WOODCUTTING, 60)
				.build()
		));

		// Fremennik
		DIARIES.add(new DiaryEntry(
			"Fremennik",
			DiaryTier.MEDIUM,
			"Fremennik Sea Boots 2 & miscellania approval bonus",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 35)
				.put(Skill.CRAFTING, 46)
				.put(Skill.DEFENCE, 40)
				.put(Skill.HUNTER, 35)
				.put(Skill.MINING, 40)
				.put(Skill.SLAYER, 47)
				.put(Skill.SMITHING, 50)
				.put(Skill.THIEVING, 42)
				.build()
		));
		DIARIES.add(new DiaryEntry(
			"Fremennik",
			DiaryTier.HARD,
			"Rellekka teleport on Boots & noted Dagannoth Bones at Waterbirth Island!",
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 45)
				.put(Skill.CRAFTING, 61)
				.put(Skill.DEFENCE, 40)
				.put(Skill.FIREMAKING, 49)
				.put(Skill.HERBLORE, 66)
				.put(Skill.HUNTER, 55)
				.put(Skill.MAGIC, 72)
				.put(Skill.MINING, 60)
				.put(Skill.SMITHING, 60)
				.put(Skill.THIEVING, 58)
				.put(Skill.WOODCUTTING, 56)
				.build()
		));
	}

	public static List<DiaryEntry> getDiaries()
	{
		return Collections.unmodifiableList(DIARIES);
	}
}
