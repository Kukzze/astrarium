package com.kukzze.astrarium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class BossIntel
{
	@Getter
	@RequiredArgsConstructor
	public static class BossGuide
	{
		private final String bossName;
		private final String difficulty;
		private final String recommendedStats;
		private final String overheadPrayers;
		private final String essentialItems;
		private final String coreMechanics;
	}

	private static final List<BossGuide> GUIDES = new ArrayList<>();

	static
	{
		GUIDES.add(new BossGuide(
			"Vorkath",
			"Intermediate / High Profit",
			"80+ Ranged, 70+ Defence, 74+ Prayer (Rigour recommended)",
			"Protect from Magic (with Anti-dragon shield) OR Protect from Missiles (with Super Antifires)",
			"Anti-dragon/Dragonfire shield, Super Antifire, Salve Amulet (ei), Crumble Undead runes, Ruby/Diamond Dragon bolts",
			"Cast Crumble Undead on Zombified Spawn instantly. Walk (don't run) in acid phase to dodge rapid fireballs."
		));

		GUIDES.add(new BossGuide(
			"Zulrah",
			"Intermediate Solo PvM",
			"75+ Magic, 75+ Ranged, 70+ Defence",
			"Protect from Missiles (Green phase), Protect from Magic (Blue phase)",
			"Toxic Blowpipe, Trident of the Swamp, Anti-venom+ potion, Ring of Suffering (recoil), 4-way gear switch",
			"Green form = Range/Melee (use Magic), Blue form = Magic/Range (use Ranged), Red form = Melee (use Magic/Ranged and stand on opposite side)."
		));

		GUIDES.add(new BossGuide(
			"The Corrupted Gauntlet (CG)",
			"Advanced / Elite Solo",
			"85+ Ranged & Magic, 80+ Defence, 70+ Prayer",
			"Hunllef changes overhead prayers every 6 player hits! Player must pray against Hunllef's current attack style (switches every 4 attacks).",
			"T2 Armour recommended for consistency, Perfected Bow & Staff, 2 Egniol potions, 16-20 combo food",
			"Listen for audio cue when Hunllef switches styles every 4 attacks. Avoid glowing floor tiles and swirling purple tornadoes."
		));

		GUIDES.add(new BossGuide(
			"Phantom Muspah",
			"Intermediate / Advanced Solo",
			"80+ Ranged & Magic, 70+ Defence",
			"Protect from Missiles / Magic depending on form",
			"Bowfa / Crossbow with Sapphire bolts (e), Ice Barrage runes or Freeze spells, Step-back tiles",
			"Freeze during melee phase to kite. Swap to Sapphire bolts (e) in shield phase to leech prayer and break shield rapidly."
		));

		GUIDES.add(new BossGuide(
			"Tombs of Amascut (ToA)",
			"Raid (Flexible 0 - 500+ Invocation)",
			"80+ All Combat Stats",
			"Varies per boss: Ba-Ba (Melee), Kephri (Ranged), Zebak (Magic/Missiles), Akkha (Cycles all styles)",
			"Keris Partisan (BiS at Kephri), Osmumten's Fang, Blowpipe, Trident, Overloads/Salts from supplies",
			"Akkha swaps protection prayers every ~80% damage. Wardens P3 requires rhythmic skull dodging and lightning tile avoidance."
		));

		GUIDES.add(new BossGuide(
			"Chambers of Xeric (CoX)",
			"Raid (1 - 100 Players)",
			"85+ All Combat Stats",
			"Olm Head: Protect from Magic (Left side) / Missiles (Right side)",
			"Dragon Warhammer / BGS (Defense reduction), Toxic Blowpipe, Trident/Bowfa, Salve Amulet (ei) for Mystics",
			"Olm Left Hand is weak to Melee, Right Hand is weak to Magic. Run Olm's head to prevent him from attacking the team."
		));

		GUIDES.add(new BossGuide(
			"Theatre of Blood (ToB)",
			"Elite Raid (Team)",
			"90+ Combat Stats, Piety & Chivalry",
			"Bloat (None/Melee), Maiden (Magic/Missiles), Verzik (Cycles P1-P3)",
			"Scythe of Vitur / Blade of Saeldor, Salve Amulet (e) for Bloat, Chinchompas for Maiden nycas, Poison cures",
			"P2 Verzik requires 4-tick bounce-back timing. Dodge green bouncing ball in team by passing to teammates."
		));

		GUIDES.add(new BossGuide(
			"Grotesque Guardians",
			"Mid-Level Slayer Boss",
			"75+ Melee & Ranged",
			"Dawn (Protect from Missiles), Dusk (Protect from Melee)",
			"Rock Hammer or Granite Hammer (CRUCIAL for final hit!), Blowpipe, Abyssal Whip",
			"Stay clear of prison fire lines in P3. Break Dusk's final form before falling ceiling debris triggers."
		));
	}

	public static List<BossGuide> getGuides()
	{
		return Collections.unmodifiableList(GUIDES);
	}
}
