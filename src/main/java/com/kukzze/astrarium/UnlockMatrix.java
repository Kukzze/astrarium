package com.kukzze.astrarium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class UnlockMatrix
{
	public enum Category
	{
		TRANSPORT("Transport Network"),
		SPELLBOOK("Spellbook"),
		PRAYER("Prayer Capability"),
		UTILITY("Game-Changing Utility");

		private final String label;

		Category(String label)
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
	public static class UnlockEntry
	{
		private final String name;
		private final Category category;
		private final String requirement;
		private final String impactDescription;
	}

	private static final List<UnlockEntry> UNLOCKS = new ArrayList<>();

	static
	{
		// Transport
		UNLOCKS.add(new UnlockEntry(
			"Fairy Rings Network",
			Category.TRANSPORT,
			"Fairytale II - Cure a Queen (partial completion)",
			"Universal teleports across 45+ locations in Gielinor via Dramen / Lunar staff."
		));
		UNLOCKS.add(new UnlockEntry(
			"Spirit Trees",
			Category.TRANSPORT,
			"Tree Gnome Village & 83 Farming (POH Spirit Tree)",
			"Instant travel between Tree Gnome Stronghold, Grand Exchange, Brimhaven, and your house."
		));
		UNLOCKS.add(new UnlockEntry(
			"Magic Mushtrees",
			Category.TRANSPORT,
			"Bone Voyage quest",
			"Instant travel across Fossil Island (House on the Hill, Mushroom Meadow, Verdant Valley)."
		));
		UNLOCKS.add(new UnlockEntry(
			"Quetzal Transport System",
			Category.TRANSPORT,
			"Twilight's Promise / Varlamore whistle",
			"Fast regional flight between Fortis Colosseum, Hunter's Guild, and Cam Torum."
		));
		UNLOCKS.add(new UnlockEntry(
			"Gnome Gliders",
			Category.TRANSPORT,
			"The Grand Tree quest",
			"Quick aerial flights to Karamja, Al Kharid, White Wolf Mountain, and Feldip Hills."
		));

		// Spellbooks
		UNLOCKS.add(new UnlockEntry(
			"Ancient Magicks",
			Category.SPELLBOOK,
			"Desert Treasure I",
			"Combat teleports, multi-target Ice Barrage, Blood Barrage healing, and Smoke spells."
		));
		UNLOCKS.add(new UnlockEntry(
			"Lunar Spellbook",
			Category.SPELLBOOK,
			"Lunar Diplomacy",
			"Vengeance, NPC Contact, Bake Pie, Humidify, Plank Make, and Moonclan teleports."
		));
		UNLOCKS.add(new UnlockEntry(
			"Arceuus Spellbook",
			Category.SPELLBOOK,
			"A Kingdom Divided",
			"Thrall summoning (free passive DPS in raids), Death Charge, Mark of Darkness, and Reanimation."
		));

		// Prayers
		UNLOCKS.add(new UnlockEntry(
			"Piety",
			Category.PRAYER,
			"King's Ransom & Knight Waves Training Grounds (70 Def/Pray)",
			"+25% Defence, +23% Strength, +20% Attack. The defining melee prayer of OSRS."
		));
		UNLOCKS.add(new UnlockEntry(
			"Rigour",
			Category.PRAYER,
			"Dexterous Prayer Scroll from CoX (74 Prayer & 70 Defence)",
			"+20% Ranged attack, +23% Ranged strength, +25% Defence. Single biggest ranged DPS upgrade!"
		));
		UNLOCKS.add(new UnlockEntry(
			"Augury",
			Category.PRAYER,
			"Arcane Prayer Scroll from CoX (77 Prayer & 70 Defence)",
			"+25% Magic attack, +25% Defence, +25% Magic defense bonus."
		));

		// Utilities
		UNLOCKS.add(new UnlockEntry(
			"Dragon Defender",
			Category.UTILITY,
			"Warriors' Guild (130 combined Attack + Strength)",
			"BiS untradeable off-hand melee accuracy and strength bonus."
		));
		UNLOCKS.add(new UnlockEntry(
			"Barrows Gloves",
			Category.UTILITY,
			"Recipe for Disaster (all 8 subquests + 175 QP)",
			"+12 All attack, +12 Strength, +12 All defense. Universal hybrid gloves."
		));
		UNLOCKS.add(new UnlockEntry(
			"Ava's Assembler",
			Category.UTILITY,
			"Dragon Slayer II & Vorkath's Head",
			"Best-in-slot Ranged cape with +2 Ranged Strength and 80% ammo retrieval."
		));
	}

	public static List<UnlockEntry> getUnlocks(Category category)
	{
		List<UnlockEntry> list = new ArrayList<>();
		for (UnlockEntry entry : UNLOCKS)
		{
			if (category == null || entry.getCategory() == category)
			{
				list.add(entry);
			}
		}
		return Collections.unmodifiableList(list);
	}
}
