package com.kukzze.astrarium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.ItemID;

public class GearProgression
{
	public enum Style
	{
		MELEE("Melee"),
		RANGED("Ranged"),
		MAGIC("Magic");

		private final String label;

		Style(String label)
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
	public static class GearUpgrade
	{
		private final int itemId;
		private final String name;
		private final Style style;
		private final int tier; // 1 (Early) to 5 (Endgame)
		private final String slot;
		private final String bonusDescription;
		private final String requirementOrSource;
	}

	private static final List<GearUpgrade> UPGRADES = new ArrayList<>();

	static
	{
		// MELEE UPGRADES
		// Tier 1 (Early Essentials)
		UPGRADES.add(new GearUpgrade(
			ItemID.DRAGON_DEFENDER,
			"Dragon Defender",
			Style.MELEE,
			1,
			"Shield",
			"+6 Str, +25 Slash/Stab Attack (Massive off-hand boost)",
			"Warriors' Guild (130 Combined Att + Str)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.FIGHTER_TORSO,
			"Fighter Torso",
			Style.MELEE,
			1,
			"Body",
			"+4 Strength bonus (Matches Bandos Chestplate!)",
			"Barbarian Assault minigame (Level 40 Defence)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.FIRE_CAPE,
			"Fire Cape",
			Style.MELEE,
			1,
			"Cape",
			"+4 Str, +1 All attack, +11 All defence",
			"Defeat TzTok-Jad in the Fight Caves"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.BERSERKER_RING_I,
			"Berserker Ring (i)",
			Style.MELEE,
			1,
			"Ring",
			"+8 Strength bonus (Double ring bonus via imbue)",
			"Dagannoth Rex (Imbued via NMZ / PvP Arena)"
		));

		// Tier 2 (Mid Game Workhorses)
		UPGRADES.add(new GearUpgrade(
			ItemID.ABYSSAL_WHIP,
			"Abyssal Whip",
			Style.MELEE,
			2,
			"Weapon",
			"+82 Slash, 4-tick attack speed (Iconic PvM staple)",
			"Level 70 Attack (Abyssal Demons drop)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.AMULET_OF_FURY,
			"Amulet of Fury",
			Style.MELEE,
			2,
			"Neck",
			"+8 Str, +10 All attack, +15 All defence, +5 Prayer",
			"Crafted from Onyx (Level 80+ Crafting/Magic)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.HELM_OF_NEITIZNOT,
			"Helm of Neitiznot",
			Style.MELEE,
			2,
			"Head",
			"+3 Strength bonus, +3 Prayer, no negative bonuses",
			"The Fremennik Isles quest"
		));

		// Tier 3 (High Tier PvM)
		UPGRADES.add(new GearUpgrade(
			ItemID.AMULET_OF_TORTURE,
			"Amulet of Torture",
			Style.MELEE,
			3,
			"Neck",
			"+10 Strength, +15 All melee attack (BiS melee amulet)",
			"Level 75 HP, crafted from Zenyte"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.FEROCIOUS_GLOVES,
			"Ferocious Gloves",
			Style.MELEE,
			3,
			"Hands",
			"+14 Strength bonus, +16 Melee attack",
			"Hydra Leather drop from Alchemical Hydra (95 Slayer)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.PRIMORDIAL_BOOTS,
			"Primordial Boots",
			Style.MELEE,
			3,
			"Feet",
			"+5 Strength, +2 Attack, best-in-slot boots",
			"Primordial Crystal from Cerberus (91 Slayer)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.OSMUMTENS_FANG,
			"Osmumten's Fang",
			Style.MELEE,
			3,
			"Weapon",
			"Rerolls accuracy on missed hits, pierces high-def bosses",
			"Tombs of Amascut (Level 82 Attack)"
		));

		// Tier 4 & 5 (Endgame / Megarare)
		UPGRADES.add(new GearUpgrade(
			ItemID.TORVA_FULL_HELM,
			"Torva Armour Set",
			Style.MELEE,
			4,
			"Set",
			"+18 Total Strength, massive defensive bonuses & Max HP boost",
			"Nex (Ancient Prison, 80 Def/Str)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.SCYTHE_OF_VITUR,
			"Scythe of Vitur",
			Style.MELEE,
			5,
			"Weapon",
			"Hits large 3x3 monsters 3 times per swing (Highest DPS in game)",
			"Theatre of Blood (ToB Megarare)"
		));

		// RANGED UPGRADES
		// Tier 1 (Early)
		UPGRADES.add(new GearUpgrade(
			ItemID.AVAS_ACCUMULATOR,
			"Ava's Accumulator",
			Style.RANGED,
			1,
			"Cape",
			"+4 Ranged attack, retrieves 72% of fired ammo",
			"Animal Magnetism quest"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.RUNE_CROSSBOW,
			"Rune Crossbow",
			Style.RANGED,
			1,
			"Weapon",
			"+90 Ranged attack, fires Broad/Ruby/Diamond bolts",
			"Level 61 Ranged (Crazy Archaeologist / Fletching)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.BLACK_DHIDE_BODY,
			"Black D'hide Set",
			Style.RANGED,
			1,
			"Set",
			"+55 Ranged attack, excellent magic defense",
			"Level 70 Ranged & 40 Defence"
		));

		// Tier 2 (Mid Game)
		UPGRADES.add(new GearUpgrade(
			ItemID.TOXIC_BLOWPIPE,
			"Toxic Blowpipe",
			Style.RANGED,
			2,
			"Weapon",
			"Blazing 2-tick attack speed with darts, inflicts venom",
			"Zulrah drop (Level 75 Ranged)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.AVAS_ASSEMBLER,
			"Ava's Assembler",
			Style.RANGED,
			2,
			"Cape",
			"+8 Ranged attack, +2 Ranged strength, 80% ammo saved",
			"Dragon Slayer II & Vorkath's Head"
		));

		// Tier 3 (Late Game)
		UPGRADES.add(new GearUpgrade(
			ItemID.NECKLACE_OF_ANGUISH,
			"Necklace of Anguish",
			Style.RANGED,
			3,
			"Neck",
			"+15 Ranged attack, +5 Ranged strength (BiS ranged amulet)",
			"Level 75 HP, crafted from Zenyte"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.BOW_OF_FAERDHINEN,
			"Bow of Faerdhinen (Bowfa)",
			Style.RANGED,
			3,
			"Weapon",
			"10-tile attack range, extreme accuracy with Crystal Armour",
			"Song of the Elves & The Corrupted Gauntlet"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.CRYSTAL_BODY,
			"Crystal Armour Set",
			Style.RANGED,
			3,
			"Set",
			"+15% Bowfa damage, +30% Bowfa accuracy bonus",
			"Corrupted Gauntlet (Crystal Armour Seeds)"
		));

		// Tier 4 & 5 (Endgame / Megarare)
		UPGRADES.add(new GearUpgrade(
			ItemID.MASORI_BODY_F,
			"Masori Armour (f)",
			Style.RANGED,
			4,
			"Set",
			"+12 Ranged strength, +133 Ranged attack, fortified with Armadyl",
			"Tombs of Amascut Expert Mode (80 Ranged/Def)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.TWISTED_BOW,
			"Twisted Bow (Tbow)",
			Style.RANGED,
			5,
			"Weapon",
			"Damage & accuracy scales exponentially against high-magic bosses",
			"Chambers of Xeric (CoX Megarare)"
		));

		// MAGIC UPGRADES
		// Tier 1 (Early)
		UPGRADES.add(new GearUpgrade(
			ItemID.IBANS_STAFF_U,
			"Iban's Staff (u)",
			Style.MAGIC,
			1,
			"Weapon",
			"Hits up to 25 damage with Iban Blast (Carries early Barrows/PvM)",
			"Underground Pass quest"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.OCCULT_NECKLACE,
			"Occult Necklace",
			Style.MAGIC,
			1,
			"Neck",
			"+12 Magic attack, +5% Magic damage bonus",
			"Smoke Devils (93 Slayer or Grand Exchange)"
		));

		// Tier 2 (Mid Game)
		UPGRADES.add(new GearUpgrade(
			ItemID.TRIDENT_OF_THE_SEAS,
			"Trident of the Seas",
			Style.MAGIC,
			2,
			"Weapon",
			"Built-in 4-tick spell, frees up rune pouch for utilities",
			"Cave Krakens (Level 87 Slayer, 75 Magic)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.IMBUED_ZAMORAK_CAPE,
			"Imbued God Cape",
			Style.MAGIC,
			2,
			"Cape",
			"+15 Magic attack, +2% Magic damage bonus",
			"Mage Arena II miniquest (Level 75 Magic)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.TRIDENT_OF_THE_SWAMP,
			"Trident of the Swamp",
			Style.MAGIC,
			2,
			"Weapon",
			"Faster casting speed, hits +3 higher than Seas, inflicts venom",
			"Kraken tentacle + Trident of the Seas + Zulrah scales"
		));

		// Tier 3 (Late Game)
		UPGRADES.add(new GearUpgrade(
			ItemID.TORMENTED_BRACELET,
			"Tormented Bracelet",
			Style.MAGIC,
			3,
			"Hands",
			"+10 Magic attack, +5% Magic damage bonus (BiS magic bracelet)",
			"Level 75 HP, crafted from Zenyte"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.ELIDINIS_WARD_F,
			"Elidinis' Ward (f)",
			Style.MAGIC,
			3,
			"Shield",
			"+25 Magic attack, +5% Magic damage, fortified with Arcane Sigil",
			"Tombs of Amascut & Corporeal Beast"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.VIRTUS_ROBE_TOP,
			"Virtus Robes",
			Style.MAGIC,
			3,
			"Set",
			"+12% Ancient Magicks damage bonus (Best barrage gear)",
			"Desert Treasure II bosses (Vardorvis, Leviathan, Whisperer, Duke)"
		));

		// Tier 4 & 5 (Endgame / Megarare)
		UPGRADES.add(new GearUpgrade(
			ItemID.ANCESTRAL_ROBE_TOP,
			"Ancestral Robes",
			Style.MAGIC,
			4,
			"Set",
			"+6% Magic damage across all spellbooks, +69 Magic attack",
			"Chambers of Xeric (Level 75 Magic & Defence)"
		));
		UPGRADES.add(new GearUpgrade(
			ItemID.TUMEKENS_SHADOW,
			"Tumeken's Shadow",
			Style.MAGIC,
			5,
			"Weapon",
			"Multiplies all magic attack & magic damage by 3x (4x in Raids!)",
			"Tombs of Amascut (ToA Megarare)"
		));
	}

	public static List<GearUpgrade> getUpgrades(Style style)
	{
		List<GearUpgrade> result = new ArrayList<>();
		for (GearUpgrade up : UPGRADES)
		{
			if (up.getStyle() == style)
			{
				result.add(up);
			}
		}
		return Collections.unmodifiableList(result);
	}
}
