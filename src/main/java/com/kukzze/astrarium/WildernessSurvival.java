package com.kukzze.astrarium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class WildernessSurvival
{
	@Getter
	@RequiredArgsConstructor
	public static class SurvivalTip
	{
		private final String title;
		private final String ruleLevel; // CRUCIAL, TACTIC, GEAR, HOTSPOT
		private final String description;
	}

	private static final List<SurvivalTip> TIPS = new ArrayList<>();

	static
	{
		TIPS.add(new SurvivalTip(
			"PK Skull Prevention Toggle",
			"CRUCIAL SAFETY",
			"Go to Settings -> Controls -> Enable 'PK Skull Prevention'. This makes it impossible to attack a player and skull, completely protecting your 3 (or 4) valuable items from skull-trick scams!"
		));

		TIPS.add(new SurvivalTip(
			"Teleport Level Limits: 20 vs 30",
			"SURVIVAL MECHANIC",
			"Standard spellbook teleports only work up to Level 20 Wilderness! Dragonstone jewelry (Amulet of Glory) and the Royal Seed Pod (from MM2) work up to Level 30 Wilderness. Above 30, you must run south."
		));

		TIPS.add(new SurvivalTip(
			"Protect Item Prayer (+1 Item)",
			"DEATH PROTECTION",
			"Always keep Protect Item prayer active (Level 25 Prayer). Unskulled players protect 4 items on death instead of 3. If skulled, you protect 1 item instead of 0."
		));

		TIPS.add(new SurvivalTip(
			"Tank Gear & Escape Inventory",
			"GEAR SETUP",
			"Equip Black D'hide Body & Chaps for magic defense against freeze/teleblock. Carry Dinh's Bulwark on defensive style. Equip a Phoenix Necklace (instantly heals 30% HP if hit below 20%)."
		));

		TIPS.add(new SurvivalTip(
			"Combo Eating (Brews + Karambwans)",
			"HP SURVIVAL",
			"Eat a piece of food (Shark/Anglerfish) -> Drink Saradomin Brew sip -> Eat a Cooked Karambwan in the same server tick to recover 50+ HP instantaneously."
		));

		TIPS.add(new SurvivalTip(
			"Wilderness Chaos Altar Protocol",
			"HOTSPOT SAFETY",
			"Offer dragon bones at the Chaos Altar for a 50% chance to not consume the bone! Only carry 1 inventory of unnoted bones at a time with a burning amulet so you risk practically nothing."
		));

		TIPS.add(new SurvivalTip(
			"Wilderness Solo Bosses (Voidwaker)",
			"PVM HOTSPOT",
			"Calvar'ion (Vet'ion jr), Spindel (Venenatis jr), and Artio (Callisto jr) drop Voidwaker pieces (blade, gem, hilt). They are located in single-plus combat caves up to level 35 Wilderness."
		));
	}

	public static List<SurvivalTip> getTips()
	{
		return Collections.unmodifiableList(TIPS);
	}
}
