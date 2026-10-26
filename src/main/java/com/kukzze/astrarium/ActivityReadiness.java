package com.kukzze.astrarium;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;

@Getter
public class ActivityReadiness
{
	@Getter
	public static class ReadinessWarning
	{
		private final String title;
		private final String description;
		private final boolean isSevere;

		public ReadinessWarning(String title, String description, boolean isSevere)
		{
			this.title = title;
			this.description = description;
			this.isSevere = isSevere;
		}
	}

	private static final Set<Integer> HAMMER_IDS = ImmutableSet.of(
		ItemID.ROCK_HAMMER,
		ItemID.GRANITE_HAMMER
	);

	private static final Set<Integer> LEAF_BROAD_IDS = ImmutableSet.of(
		ItemID.LEAFBLADED_SWORD,
		ItemID.LEAFBLADED_BATTLEAXE,
		ItemID.LEAFBLADED_SPEAR,
		ItemID.BROAD_ARROWS,
		ItemID.BROAD_BOLTS,
		ItemID.AMETHYST_BROAD_BOLTS,
		ItemID.SLAYERS_STAFF,
		ItemID.SLAYERS_STAFF_E
	);

	private static final Set<Integer> SLAYER_HELMETS = ImmutableSet.of(
		ItemID.SLAYER_HELMET,
		ItemID.SLAYER_HELMET_I,
		ItemID.BLACK_SLAYER_HELMET,
		ItemID.BLACK_SLAYER_HELMET_I,
		ItemID.GREEN_SLAYER_HELMET,
		ItemID.GREEN_SLAYER_HELMET_I,
		ItemID.RED_SLAYER_HELMET,
		ItemID.RED_SLAYER_HELMET_I,
		ItemID.PURPLE_SLAYER_HELMET,
		ItemID.PURPLE_SLAYER_HELMET_I,
		ItemID.TURQUOISE_SLAYER_HELMET,
		ItemID.TURQUOISE_SLAYER_HELMET_I,
		ItemID.HYDRA_SLAYER_HELMET,
		ItemID.HYDRA_SLAYER_HELMET_I,
		ItemID.TWISTED_SLAYER_HELMET,
		ItemID.TWISTED_SLAYER_HELMET_I
	);

	private static final Set<Integer> SPECTRE_PROTECTION = ImmutableSet.<Integer>builder()
		.addAll(SLAYER_HELMETS)
		.add(ItemID.NOSE_PEG)
		.build();

	private static final Set<Integer> DUST_PROTECTION = ImmutableSet.<Integer>builder()
		.addAll(SLAYER_HELMETS)
		.add(ItemID.FACEMASK)
		.build();

	private static final Set<Integer> BANSHEE_PROTECTION = ImmutableSet.<Integer>builder()
		.addAll(SLAYER_HELMETS)
		.add(ItemID.EARMUFFS)
		.build();

	private static final Set<Integer> BASILISK_PROTECTION = ImmutableSet.of(
		ItemID.MIRROR_SHIELD,
		ItemID.VS_SHIELD
	);

	private static final Set<Integer> CAVE_HORROR_PROTECTION = ImmutableSet.<Integer>builder()
		.addAll(SLAYER_HELMETS)
		.add(ItemID.WITCHWOOD_ICON)
		.build();

	private static final Set<Integer> WYVERN_SHIELDS = ImmutableSet.of(
		ItemID.ELEMENTAL_SHIELD,
		ItemID.MIND_SHIELD,
		ItemID.DRAGONFIRE_SHIELD,
		ItemID.ANCIENT_WYVERN_SHIELD
	);

	private static final Set<Integer> DRAGONFIRE_PROTECTION = ImmutableSet.of(
		ItemID.ANTIDRAGON_SHIELD,
		ItemID.DRAGONFIRE_SHIELD,
		ItemID.ANCIENT_WYVERN_SHIELD,
		ItemID.ANTIFIRE_POTION1,
		ItemID.ANTIFIRE_POTION2,
		ItemID.ANTIFIRE_POTION3,
		ItemID.ANTIFIRE_POTION4,
		ItemID.EXTENDED_ANTIFIRE1,
		ItemID.EXTENDED_ANTIFIRE2,
		ItemID.EXTENDED_ANTIFIRE3,
		ItemID.EXTENDED_ANTIFIRE4,
		ItemID.SUPER_ANTIFIRE_POTION1,
		ItemID.SUPER_ANTIFIRE_POTION2,
		ItemID.SUPER_ANTIFIRE_POTION3,
		ItemID.SUPER_ANTIFIRE_POTION4,
		ItemID.EXTENDED_SUPER_ANTIFIRE1,
		ItemID.EXTENDED_SUPER_ANTIFIRE2,
		ItemID.EXTENDED_SUPER_ANTIFIRE3,
		ItemID.EXTENDED_SUPER_ANTIFIRE4
	);

	public static List<ReadinessWarning> auditInventory(Client client, String currentTask)
	{
		List<ReadinessWarning> warnings = new ArrayList<>();

		if (currentTask == null || currentTask.isEmpty())
		{
			return warnings;
		}

		String lower = currentTask.toLowerCase();

		// Gargoyles / Grotesque Guardians
		if (lower.contains("gargoyle") || lower.contains("grotesque"))
		{
			if (!hasAnyItem(client, HAMMER_IDS))
			{
				warnings.add(new ReadinessWarning(
					"Missing Rock Hammer",
					"Gargoyles require a Rock Hammer to smash (unless the Gargoyle Smasher unlock is purchased).",
					true
				));
			}
		}

		// Kurasks / Turoths
		if (lower.contains("kurask") || lower.contains("turoth"))
		{
			if (!hasAnyItem(client, LEAF_BROAD_IDS))
			{
				warnings.add(new ReadinessWarning(
					"Missing Leaf-Bladed Weapon / Ammo",
					"Kurasks and Turoths can only be damaged by broad ammo, leaf-bladed weapons, or magic dart.",
					true
				));
			}
		}

		// Aberrant Spectres
		if (lower.contains("spectre"))
		{
			if (!hasAnyItem(client, SPECTRE_PROTECTION))
			{
				warnings.add(new ReadinessWarning(
					"Missing Nosepeg / Slayer Helm",
					"Aberrant spectres will rapidly drain all your combat stats without a Nosepeg or Slayer helm.",
					true
				));
			}
		}

		// Dust Devils / Smoke Devils
		if (lower.contains("dust devil") || lower.contains("smoke devil"))
		{
			if (!hasAnyItem(client, DUST_PROTECTION))
			{
				warnings.add(new ReadinessWarning(
					"Missing Facemask / Slayer Helm",
					"Dust & Smoke Devils will blind and suffocate you without a Facemask or Slayer helm.",
					true
				));
			}
		}

		// Banshees
		if (lower.contains("banshee"))
		{
			if (!hasAnyItem(client, BANSHEE_PROTECTION))
			{
				warnings.add(new ReadinessWarning(
					"Missing Earmuffs / Slayer Helm",
					"Banshee screams will deafen you and drain stats without Earmuffs or Slayer helm.",
					true
				));
			}
		}

		// Basilisks
		if (lower.contains("basilisk"))
		{
			if (!hasAnyItem(client, BASILISK_PROTECTION))
			{
				warnings.add(new ReadinessWarning(
					"Missing Mirror Shield / V's Shield",
					"Basilisks will turn your gaze to stone without a Mirror Shield or V's Shield equipped.",
					true
				));
			}
		}

		// Desert Lizards
		if (lower.contains("desert lizard") || lower.contains("small lizard"))
		{
			if (!hasAnyItem(client, ImmutableSet.of(ItemID.ICE_COOLER)))
			{
				warnings.add(new ReadinessWarning(
					"Missing Ice Coolers",
					"Desert Lizards must be cooled off with Ice Coolers when weakened.",
					true
				));
			}
		}

		// Cave Horrors
		if (lower.contains("cave horror"))
		{
			if (!hasAnyItem(client, CAVE_HORROR_PROTECTION))
			{
				warnings.add(new ReadinessWarning(
					"Missing Witchwood Icon",
					"Cave Horrors will strike with fear and deal heavy unavoidable damage without a Witchwood Icon.",
					true
				));
			}
		}

		// Wyverns (Skeletal, Fossil Island)
		if (lower.contains("wyvern"))
		{
			if (!hasAnyItem(client, WYVERN_SHIELDS))
			{
				warnings.add(new ReadinessWarning(
					"Missing Wyvern Shield",
					"Wyvern icy breath will freeze you solid without an Elemental, Mind, or Ancient Wyvern Shield.",
					true
				));
			}
		}

		// Dragons (Green, Blue, Red, Black, Iron, Steel, Mithril, Adamant, Rune, Vorkath, KBD)
		if (lower.contains("dragon") || lower.contains("vorkath") || lower.contains("king black"))
		{
			if (!hasAnyItem(client, DRAGONFIRE_PROTECTION))
			{
				warnings.add(new ReadinessWarning(
					"Missing Dragonfire Protection",
					"Dragons breathe lethal fire (50+ damage)! Carry an Anti-dragon shield or Antifire potion.",
					true
				));
			}
		}

		return warnings;
	}

	private static boolean hasAnyItem(Client client, Set<Integer> ids)
	{
		return containerContains(client.getItemContainer(InventoryID.INVENTORY), ids)
			|| containerContains(client.getItemContainer(InventoryID.EQUIPMENT), ids);
	}

	private static boolean containerContains(ItemContainer container, Set<Integer> ids)
	{
		if (container == null)
		{
			return false;
		}

		for (Item item : container.getItems())
		{
			if (item != null && ids.contains(item.getId()))
			{
				return true;
			}
		}
		return false;
	}
}
