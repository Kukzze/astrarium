package com.kukzze.astrarium;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.runelite.api.Skill;

public class MilestoneRepository
{
	private static final List<Milestone> ALL_MILESTONES = new ArrayList<>();

	static
	{
		// Early Game
		ALL_MILESTONES.add(new Milestone(
			"Protection Prayers",
			"Unlock Protect from Melee, Missiles, and Magic",
			Milestone.Tier.EARLY,
			ImmutableMap.of(Skill.PRAYER, 43)
		));

		ALL_MILESTONES.add(new Milestone(
			"Fairy Rings Access",
			"Unlock the universal Gielinor teleportation network",
			Milestone.Tier.EARLY,
			ImmutableMap.of(Skill.MAGIC, 59, Skill.CRAFTING, 31)
		));

		ALL_MILESTONES.add(new Milestone(
			"Graceful Outfit Ready",
			"Achieve Rooftop Agility mastery for energy recovery",
			Milestone.Tier.EARLY,
			ImmutableMap.of(Skill.AGILITY, 50)
		));

		// Mid Game
		ALL_MILESTONES.add(new Milestone(
			"Barrows Gloves",
			"Bis versatile gloves unlocked from Recipe for Disaster",
			Milestone.Tier.MID,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.COOKING, 70)
				.put(Skill.AGILITY, 48)
				.put(Skill.MINING, 50)
				.put(Skill.MAGIC, 59)
				.put(Skill.THIEVING, 53)
				.put(Skill.WOODCUTTING, 50)
				.build()
		));

		ALL_MILESTONES.add(new Milestone(
			"Piety Prayer",
			"Ultimate melee prayer granting +25% Defence, +23% Strength, +20% Attack",
			Milestone.Tier.MID,
			ImmutableMap.of(Skill.PRAYER, 70, Skill.DEFENCE, 70)
		));

		ALL_MILESTONES.add(new Milestone(
			"Fire Cape Ready",
			"Sufficient combat stats to conquer TzHaar Fight Caves",
			Milestone.Tier.MID,
			ImmutableMap.of(Skill.RANGED, 75, Skill.PRAYER, 43, Skill.DEFENCE, 70)
		));

		ALL_MILESTONES.add(new Milestone(
			"Dragon Slayer II",
			"Unlock Vorkath, Myth's Guild, and Ava's Assembler",
			Milestone.Tier.MID,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.MAGIC, 75)
				.put(Skill.SMITHING, 70)
				.put(Skill.MINING, 68)
				.put(Skill.CRAFTING, 62)
				.put(Skill.AGILITY, 60)
				.put(Skill.THIEVING, 60)
				.build()
		));

		// Late Game
		ALL_MILESTONES.add(new Milestone(
			"Song of the Elves",
			"Unlock Prifddinas, The Corrupted Gauntlet, and Bowfa",
			Milestone.Tier.LATE,
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

		ALL_MILESTONES.add(new Milestone(
			"Maxed POH (Pool & Nexus)",
			"Infinite teleports, stamina, prayer, and stat restoration in your house",
			Milestone.Tier.LATE,
			ImmutableMap.of(Skill.CONSTRUCTION, 83)
		));

		ALL_MILESTONES.add(new Milestone(
			"Abyssal Whip / Trident",
			"Core Slayer PvM drops unlocked",
			Milestone.Tier.LATE,
			ImmutableMap.of(Skill.SLAYER, 85)
		));

		// Endgame
		ALL_MILESTONES.add(new Milestone(
			"High Raids & Colosseum",
			"Stats recommended for expert ToA, CoX, ToB, and Sol Heredit",
			Milestone.Tier.ENDGAME,
			ImmutableMap.of(
				Skill.ATTACK, 90,
				Skill.STRENGTH, 95,
				Skill.RANGED, 95,
				Skill.MAGIC, 95,
				Skill.PRAYER, 77
			)
		));

		ALL_MILESTONES.add(new Milestone(
			"Max Cape",
			"Level 99 in all 23 skills (Total Level 2277)",
			Milestone.Tier.ENDGAME,
			ImmutableMap.<Skill, Integer>builder()
				.put(Skill.ATTACK, 99)
				.put(Skill.STRENGTH, 99)
				.put(Skill.DEFENCE, 99)
				.put(Skill.RANGED, 99)
				.put(Skill.PRAYER, 99)
				.put(Skill.MAGIC, 99)
				.put(Skill.HITPOINTS, 99)
				.build()
		));
	}

	public static List<Milestone> getMilestones()
	{
		return Collections.unmodifiableList(ALL_MILESTONES);
	}
}
