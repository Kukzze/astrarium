package com.kukzze.astrarium;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Client;
import net.runelite.api.Quest;
import net.runelite.api.QuestState;
import net.runelite.api.Skill;

public class OptimalQuestGuide
{
	@Getter
	@RequiredArgsConstructor
	public static class QuestEntry
	{
		private final Quest quest;
		private final String title;
		private final String rewardHighlight;
		private final int questPoints;
		private final Map<Skill, Integer> skillRequirements;

		public QuestState getState(Client client)
		{
			if (client == null)
			{
				return QuestState.NOT_STARTED;
			}
			try
			{
				return quest.getState(client);
			}
			catch (Exception ex)
			{
				return QuestState.NOT_STARTED;
			}
		}

		public boolean meetsSkillRequirements(Map<Skill, Integer> currentLevels)
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
			return missing.isEmpty() ? "Ready!" : "Needs: " + String.join(", ", missing);
		}
	}

	private static final List<QuestEntry> OPTIMAL_QUESTS = new ArrayList<>();

	static
	{
		// Early Game XP Bombs (Jumpstarts fresh accounts past early grind)
		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.WATERFALL_QUEST,
			"Waterfall Quest",
			"+13,750 Att & Str XP (Instant Lvl 30 Melee)",
			1,
			ImmutableMap.of()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.WITCHS_HOUSE,
			"Witch's House",
			"+6,325 HP XP (Lvl 10 -> 24 Hitpoints)",
			4,
			ImmutableMap.of()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.THE_KNIGHTS_SWORD,
			"The Knight's Sword",
			"+12,725 Smithing XP (Lvl 1 -> 29 Smithing)",
			1,
			ImmutableMap.of(Skill.MINING, 10)
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.FIGHT_ARENA,
			"Fight Arena",
			"+12,175 Attack XP & +2,175 Thieving XP",
			2,
			ImmutableMap.of()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.TREE_GNOME_VILLAGE,
			"Tree Gnome Village",
			"+11,450 Attack XP & Spirit Tree Travel",
			2,
			ImmutableMap.of()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.SEA_SLUG,
			"Sea Slug",
			"+7,175 Fishing XP (Lvl 1 -> 24 Fishing)",
			1,
			ImmutableMap.of(Skill.FIREMAKING, 30)
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.THE_GRAND_TREE,
			"The Grand Tree",
			"+18,400 Att, +7,900 Agility, +2,150 Magic XP",
			5,
			ImmutableMap.of(Skill.AGILITY, 25)
		));

		// Core Utility & Unlock Quests
		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.PRIEST_IN_PERIL,
			"Priest in Peril",
			"Unlocks Morytania & Canifis access",
			1,
			ImmutableMap.of()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.NATURE_SPIRIT,
			"Nature Spirit",
			"Mort Myre access & Druidic pouches",
			2,
			ImmutableMap.of(Skill.CRAFTING, 18)
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.FAIRYTALE_I__GROWING_PAINS,
			"Fairytale I - Growing Pains",
			"Magic Secateurs & Farming XP bonus",
			2,
			ImmutableMap.of()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.FAIRYTALE_II__CURE_A_QUEEN,
			"Fairytale II - Cure a Queen",
			"Universal Fairy Ring Teleport Network",
			2,
			ImmutableMap.of(Skill.THIEVING, 40, Skill.FARMING, 49, Skill.HERBLORE, 57)
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.HOLY_GRAIL,
			"Holy Grail",
			"+11,000 Prayer & +15,300 Defence XP",
			2,
			ImmutableMap.of(Skill.ATTACK, 30)
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.HORROR_FROM_THE_DEEP,
			"Horror from the Deep",
			"Unlocks God Books (Holy, Unholy, Book of War)",
			2,
			ImmutableMap.of(Skill.AGILITY, 35)
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.ANIMAL_MAGNETISM,
			"Animal Magnetism",
			"Ava's Attractor / Accumulator (Ammo retrieval)",
			8,
			ImmutableMap.of(Skill.SLAYER, 18, Skill.CRAFTING, 19, Skill.RANGED, 30, Skill.WOODCUTTING, 35)
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.MONKEY_MADNESS_I,
			"Monkey Madness I",
			"Dragon Scimitar wield & +35,000 Combat XP",
			3,
			ImmutableMap.of()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.LUNAR_DIPLOMACY,
			"Lunar Diplomacy",
			"Lunar Spellbook (Vengeance, NPC Contact, Bake Pie)",
			2,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.CRAFTING, 61)
				.put(Skill.DEFENCE, 40)
				.put(Skill.FIREMAKING, 49)
				.put(Skill.MAGIC, 65)
				.put(Skill.MINING, 60)
				.put(Skill.WOODCUTTING, 55)
				.build()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.RECIPE_FOR_DISASTER,
			"Recipe for Disaster",
			"Barrows Gloves (BiS hybrid gloves)",
			10,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.COOKING, 70)
				.put(Skill.AGILITY, 48)
				.put(Skill.MINING, 50)
				.put(Skill.MAGIC, 59)
				.put(Skill.THIEVING, 53)
				.put(Skill.WOODCUTTING, 50)
				.build()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.SINS_OF_THE_FATHER,
			"Sins of the Father",
			"Darkmeyer, Blisterwood Flail, & Daeyalt Essence",
			2,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.WOODCUTTING, 62)
				.put(Skill.FLETCHING, 60)
				.put(Skill.SLAYER, 56)
				.put(Skill.AGILITY, 56)
				.put(Skill.ATTACK, 50)
				.put(Skill.MAGIC, 49)
				.build()
		));

		// Grandmaster Masterpieces
		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.DRAGON_SLAYER_II,
			"Dragon Slayer II",
			"Vorkath, Ava's Assembler, Myth's Guild",
			5,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.MAGIC, 75)
				.put(Skill.SMITHING, 70)
				.put(Skill.MINING, 68)
				.put(Skill.CRAFTING, 62)
				.put(Skill.AGILITY, 60)
				.put(Skill.THIEVING, 60)
				.build()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.SONG_OF_THE_ELVES,
			"Song of the Elves",
			"Prifddinas, Corrupted Gauntlet, Bowfa & Blade",
			5,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.AGILITY, 70)
				.put(Skill.CONSTRUCTION, 70)
				.put(Skill.FARMING, 70)
				.put(Skill.HERBLORE, 70)
				.put(Skill.HUNTER, 70)
				.put(Skill.MINING, 70)
				.put(Skill.SMITHING, 70)
				.put(Skill.WOODCUTTING, 70)
				.build()
		));

		OPTIMAL_QUESTS.add(new QuestEntry(
			Quest.DESERT_TREASURE_II__THE_FALLEN_EMPIRE,
			"Desert Treasure II",
			"4 Ancient Bosses (Vardorvis, Leviathan, Whisperer, Duke)",
			5,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.MAGIC, 75)
				.put(Skill.FIREMAKING, 75)
				.put(Skill.THIEVING, 70)
				.put(Skill.HERBLORE, 70)
				.put(Skill.RUNECRAFT, 60)
				.put(Skill.CONSTRUCTION, 60)
				.build()
		));
	}

	public static List<QuestEntry> getOptimalQuests()
	{
		return Collections.unmodifiableList(OPTIMAL_QUESTS);
	}
}
