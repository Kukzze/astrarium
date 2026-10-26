package com.kukzze.astrarium;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.Skill;

public class OptimalLevelingGuide
{
	@Getter
	@RequiredArgsConstructor
	public static class TrainingMethod
	{
		private final int minLevel;
		private final int maxLevel;
		private final String name;
		private final String rate;
		private final String location;
		private final String notes;
	}

	private static final Map<Skill, List<TrainingMethod>> METHODS_BY_SKILL = new EnumMap<>(Skill.class);

	static
	{
		// AGILITY
		List<TrainingMethod> agility = new ArrayList<>();
		agility.add(new TrainingMethod(1, 10, "Gnome Stronghold Course", "~8k XP/hr", "Tree Gnome Stronghold", "Equip lightweight gear or graceful."));
		agility.add(new TrainingMethod(10, 20, "Draynor Village Rooftop", "~9k XP/hr", "Draynor Village", "First rooftop course, pick up Marks of Grace."));
		agility.add(new TrainingMethod(20, 30, "Al Kharid Rooftop", "~14k XP/hr", "Al Kharid", "Use ring of dueling to reset stamina if needed."));
		agility.add(new TrainingMethod(30, 40, "Varrock Rooftop", "~13k XP/hr", "Varrock East Bank", "Decent marks rate."));
		agility.add(new TrainingMethod(40, 60, "Canifis Rooftop Course", "~19k XP/hr", "Canifis, Morytania", "Highest Mark of Grace rate in the game! Stay until full Graceful."));
		agility.add(new TrainingMethod(60, 80, "Seers' Village Rooftop", "~45k - 56k XP/hr", "Seers' Village", "Complete Kandarin Hard Diary for Camelot Bank teleport boost."));
		agility.add(new TrainingMethod(80, 90, "Rellekka Rooftop Course", "~54k XP/hr", "Rellekka", "Fremennik boots 4 provide unlimited teleports."));
		agility.add(new TrainingMethod(90, 99, "Ardougne Rooftop Course", "~62k XP/hr", "East Ardougne", "Fastest agility XP and highest level marks rate."));
		METHODS_BY_SKILL.put(Skill.AGILITY, agility);

		// THIEVING
		List<TrainingMethod> thieving = new ArrayList<>();
		thieving.add(new TrainingMethod(1, 5, "Men & Women", "~5k XP/hr", "Lumbridge / Edgeville", "Basic starting pickpocketing."));
		thieving.add(new TrainingMethod(5, 25, "Bakery Stalls", "~18k XP/hr", "East Ardougne Market", "Steal cakes for free food."));
		thieving.add(new TrainingMethod(25, 45, "Fruit Stalls", "~35k XP/hr", "Hosidius, Kourend", "Trap the dogs for non-stop stealing."));
		thieving.add(new TrainingMethod(45, 65, "Blackjacking / Varlamore", "~80k - 130k XP/hr", "Pollnivneach / Varlamore", "Use Blackjack or Varlamore wealthy citizens."));
		thieving.add(new TrainingMethod(65, 99, "Ardougne Knights", "~150k - 250k XP/hr", "Ardougne Market / Bank", "Equip Rogue's Outfit (doubles loot) & Ardy Medium Diary."));
		METHODS_BY_SKILL.put(Skill.THIEVING, thieving);

		// PRAYER
		List<TrainingMethod> prayer = new ArrayList<>();
		prayer.add(new TrainingMethod(1, 43, "Dragon Bones (Chaos Altar)", "~350k XP/hr", "Wilderness Chaos Altar", "50% chance to save bones! Take 1 inventory at a time."));
		prayer.add(new TrainingMethod(43, 70, "Dragon Bones / Dagannoth", "~450k XP/hr", "Chaos Altar / POH Gilded", "Aim for 70 to unlock Piety."));
		prayer.add(new TrainingMethod(70, 77, "Superior Dragon Bones", "~700k XP/hr", "POH Gilded / Chaos Altar", "Unlocks Rigour & Augury prayer scrolls."));
		prayer.add(new TrainingMethod(77, 99, "Superior Dragon Bones", "~800k XP/hr", "POH Gilded Altar", "Safe training to 99 in player-owned house."));
		METHODS_BY_SKILL.put(Skill.PRAYER, prayer);

		// CONSTRUCTION
		List<TrainingMethod> construction = new ArrayList<>();
		construction.add(new TrainingMethod(1, 19, "Crude Wooden Chairs", "~30k XP/hr", "Player-Owned House", "Use ordinary planks and nails."));
		construction.add(new TrainingMethod(19, 33, "Oak Chairs", "~60k XP/hr", "Player-Owned House", "Switch to oak planks."));
		construction.add(new TrainingMethod(33, 52, "Oak Larders", "~150k XP/hr", "POH Kitchen", "Fast, cheap oak XP."));
		construction.add(new TrainingMethod(52, 83, "Mahogany Tables / Homes", "~250k - 500k XP/hr", "POH / Mahogany Homes", "Target 83 for Rejuvenation Pool, Occult Altar, & Nexus!"));
		construction.add(new TrainingMethod(83, 99, "Gnome Benches / Tables", "~600k+ XP/hr", "POH Superior Garden", "Blazing fast XP to 99 Cape."));
		METHODS_BY_SKILL.put(Skill.CONSTRUCTION, construction);

		// HUNTER
		List<TrainingMethod> hunter = new ArrayList<>();
		hunter.add(new TrainingMethod(1, 9, "Crimson Swifts", "~8k XP/hr", "Feldip Hills", "Use bird snares."));
		hunter.add(new TrainingMethod(9, 19, "Copper Longtails", "~14k XP/hr", "Piscatoris Hunter Area", "Bird snaring."));
		hunter.add(new TrainingMethod(19, 46, "Tropical Wagtails & Falconry", "~35k XP/hr", "Feldip Hills / Piscatoris", "Catch kebbits with falconer."));
		hunter.add(new TrainingMethod(46, 63, "Varlamore Hunter Rumours", "~65k - 90k XP/hr", "Hunter's Guild, Varlamore", "Amazing rewards, guild meats, and hunter outfit."));
		hunter.add(new TrainingMethod(63, 99, "Red / Black Chinchompas", "~120k - 200k XP/hr", "Feldip Hills / Wilderness", "Highly profitable ranged ammo."));
		METHODS_BY_SKILL.put(Skill.HUNTER, hunter);

		// MAGIC
		List<TrainingMethod> magic = new ArrayList<>();
		magic.add(new TrainingMethod(1, 25, "Strike Spells / Splashing", "~10k XP/hr", "Lumbridge / Seagulls", "Air and Fire strikes."));
		magic.add(new TrainingMethod(25, 55, "Teleport Training", "~60k XP/hr", "Varrock / Camelot", "Spam Varrock and Camelot teleports with air staff."));
		magic.add(new TrainingMethod(55, 62, "High Level Alchemy", "~70k XP/hr", "Any Bank / Agility", "Alch yew/magic longbows or d'hide bodies."));
		magic.add(new TrainingMethod(62, 99, "Bursting & Barraging", "~200k - 350k XP/hr", "Maniacal Monkeys / Dust Devils", "Ice Burst / Barrage on stacked monkeys with prayer gear."));
		METHODS_BY_SKILL.put(Skill.MAGIC, magic);

		// RANGED
		List<TrainingMethod> ranged = new ArrayList<>();
		ranged.add(new TrainingMethod(1, 40, "Shortbow & Iron Knives", "~25k XP/hr", "Sand Crabs / Rock Crabs", "High attack speed weapons give rapid early XP."));
		ranged.add(new TrainingMethod(40, 75, "Rune Crossbow / Magic Short", "~50k - 70k XP/hr", "Ammonite Crabs / Slayer", "Use Broad Bolts or Amethyst arrows with Ava's Accumulator."));
		ranged.add(new TrainingMethod(75, 99, "Chinchompas / Blowpipe", "~300k - 700k XP/hr", "Maniacal Monkey Caves", "Red/Black chins in MM2 caves; or Toxic Blowpipe on Slayer."));
		METHODS_BY_SKILL.put(Skill.RANGED, ranged);

		// MELEE (Attack, Strength, Defence)
		List<TrainingMethod> melee = new ArrayList<>();
		melee.add(new TrainingMethod(1, 30, "Waterfall Quest & Fight Arena", "Instant Lvl 30", "Baxtorian Falls", "Quests grant 25,000+ Attack XP with zero combat grind!"));
		melee.add(new TrainingMethod(30, 65, "Sand Crabs / Ammonite Crabs", "~45k - 65k XP/hr", "Hosidius / Fossil Island", "AFK 10-minute aggro with Dragon Scimitar."));
		melee.add(new TrainingMethod(65, 99, "Slayer Tasks / NMZ / Scurrius", "~80k - 120k XP/hr", "Various / Nightmare Zone", "Train through Slayer for unlocks; or NMZ/Scurrius for fast raw XP."));
		METHODS_BY_SKILL.put(Skill.ATTACK, melee);
		METHODS_BY_SKILL.put(Skill.STRENGTH, melee);
		METHODS_BY_SKILL.put(Skill.DEFENCE, melee);

		// HERBLORE
		List<TrainingMethod> herblore = new ArrayList<>();
		herblore.add(new TrainingMethod(1, 26, "Druidic Ritual & Attack Pots", "~40k XP/hr", "Any Bank", "Eye of newt + Guam."));
		herblore.add(new TrainingMethod(26, 38, "Energy Potions", "~80k XP/hr", "Any Bank", "Chocolate dust + Harralander."));
		herblore.add(new TrainingMethod(38, 55, "Prayer Potions", "~160k XP/hr", "Any Bank", "Snape grass + Ranarr weed."));
		herblore.add(new TrainingMethod(55, 70, "Super Strength / Restores", "~220k XP/hr", "Any Bank", "Kwuarm / Snapdragon."));
		herblore.add(new TrainingMethod(70, 99, "Saradomin Brews", "~350k XP/hr", "Any Bank", "Crushed bird nest + Toadflax."));
		METHODS_BY_SKILL.put(Skill.HERBLORE, herblore);

		// FARMING
		List<TrainingMethod> farming = new ArrayList<>();
		farming.add(new TrainingMethod(1, 15, "Bagged Plants / Allotments", "~15k XP/run", "POH / Falador Farm", "Early potatoes and onions."));
		farming.add(new TrainingMethod(15, 30, "Oak Trees & Apple Trees", "~25k XP/run", "Tree Patches", "Plant once, check health next day."));
		farming.add(new TrainingMethod(30, 60, "Willow / Maple & Fruit Trees", "~60k XP/run", "Tree Patches & Gnome Stronghold", "Daily tree runs take 5 minutes for huge XP."));
		farming.add(new TrainingMethod(60, 99, "Yew / Magic & Palm / Dragonfruit", "~150k+ XP/run", "Tree Patches & Farming Guild", "Combine with Herb runs (Ranarr/Snapdragon) for profit."));
		METHODS_BY_SKILL.put(Skill.FARMING, farming);

		// WOODCUTTING
		List<TrainingMethod> woodcutting = new ArrayList<>();
		woodcutting.add(new TrainingMethod(1, 15, "Regular Trees", "~10k XP/hr", "Lumbridge", "Use best available axe."));
		woodcutting.add(new TrainingMethod(15, 35, "Oak Trees", "~25k XP/hr", "Draynor Village", "Fast respawns."));
		woodcutting.add(new TrainingMethod(35, 60, "Willow / Teak Trees", "~45k - 60k XP/hr", "Draynor / Fossil Island", "Teaks are best Woodcutting XP."));
		woodcutting.add(new TrainingMethod(60, 99, "Teaks (1.5t/AFK) / Sulliuscep", "~70k - 100k XP/hr", "Fossil Island", "Sulliusceps give fossils and great passive prayer XP."));
		METHODS_BY_SKILL.put(Skill.WOODCUTTING, woodcutting);

		// FISHING
		List<TrainingMethod> fishing = new ArrayList<>();
		fishing.add(new TrainingMethod(1, 20, "Small Net (Shrimp / Anchovies)", "~10k XP/hr", "Draynor / Lumbridge Swamp", "Basic net fishing."));
		fishing.add(new TrainingMethod(20, 58, "Fly Fishing (Trout / Salmon)", "~30k - 45k XP/hr", "Barbarian Village", "Feathers + Fly fishing rod."));
		fishing.add(new TrainingMethod(58, 99, "Barbarian Fishing / Tempoross", "~60k - 80k XP/hr", "Otto's Grotto / Tempoross", "Barbarian fishing grants free Agility and Strength XP!"));
		METHODS_BY_SKILL.put(Skill.FISHING, fishing);

		// COOKING
		List<TrainingMethod> cooking = new ArrayList<>();
		cooking.add(new TrainingMethod(1, 20, "Shrimp & Meat", "~20k XP/hr", "Lumbridge Castle Range", "Low burn rate on castle range."));
		cooking.add(new TrainingMethod(20, 60, "Trout & Salmon", "~80k - 120k XP/hr", "Hosidius Range / Rogues Den", "Cook raw fish from barbarian fishing."));
		cooking.add(new TrainingMethod(60, 80, "Raw Karambwan (1-tick/AFK)", "~250k - 700k XP/hr", "Hosidius Kitchen", "Karambwans have practically zero burn rate with cooking gauntlets."));
		cooking.add(new TrainingMethod(80, 99, "Shark & Wine Making", "~300k - 500k XP/hr", "Hosidius Kitchen / Grand Exchange", "Grapes + Jugs of water for fast wines, or raw sharks."));
		METHODS_BY_SKILL.put(Skill.COOKING, cooking);

		// MINING
		List<TrainingMethod> mining = new ArrayList<>();
		mining.add(new TrainingMethod(1, 15, "Copper & Tin Ore", "~10k XP/hr", "Lumbridge Swamp Mine", "Basic pickaxe."));
		mining.add(new TrainingMethod(15, 70, "Iron Ore / Motherlode Mine", "~40k - 55k XP/hr", "Al Kharid / Motherlode Mine", "Motherlode gives golden nuggets for Prospector & Coal bag."));
		mining.add(new TrainingMethod(70, 99, "Volcanic Mine / Blast Mine", "~80k - 95k XP/hr", "Fossil Island / Lovakengj", "Volcanic Mine is relaxing and high XP with a group."));
		METHODS_BY_SKILL.put(Skill.MINING, mining);

		// SMITHING
		List<TrainingMethod> smithing = new ArrayList<>();
		smithing.add(new TrainingMethod(1, 29, "The Knight's Sword Quest", "Instant Lvl 29", "Falador White Knights", "Skips early smithing entirely with 12,725 XP!"));
		smithing.add(new TrainingMethod(29, 70, "Giants' Foundry", "~120k - 180k XP/hr", "Kharidian Desert", "Massive XP per bar and great profit/unlocks."));
		smithing.add(new TrainingMethod(70, 99, "Blast Furnace (Gold Bars)", "~300k - 380k XP/hr", "Keldagrim Blast Furnace", "Goldsmith gauntlets grant 2.5x XP multiplier! Fastest 99."));
		METHODS_BY_SKILL.put(Skill.SMITHING, smithing);
	}

	public static TrainingMethod getOptimalMethod(Skill skill, int currentLevel)
	{
		List<TrainingMethod> methods = METHODS_BY_SKILL.get(skill);
		if (methods == null || methods.isEmpty())
		{
			return null;
		}

		for (TrainingMethod m : methods)
		{
			if (currentLevel >= m.getMinLevel() && currentLevel < m.getMaxLevel())
			{
				return m;
			}
		}

		// If level >= 99 or above highest bracket, return top bracket
		return methods.get(methods.size() - 1);
	}

	public static TrainingMethod getNextMethod(Skill skill, int currentLevel)
	{
		List<TrainingMethod> methods = METHODS_BY_SKILL.get(skill);
		if (methods == null || methods.isEmpty())
		{
			return null;
		}

		for (int i = 0; i < methods.size() - 1; i++)
		{
			TrainingMethod curr = methods.get(i);
			if (currentLevel >= curr.getMinLevel() && currentLevel < curr.getMaxLevel())
			{
				return methods.get(i + 1);
			}
		}

		return null;
	}
}
