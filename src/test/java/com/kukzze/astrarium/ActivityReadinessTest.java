package com.kukzze.astrarium;

import java.util.List;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActivityReadinessTest
{
	@Test
	public void testGargoyleWarningWhenMissingHammer()
	{
		Client client = mock(Client.class);
		ItemContainer inv = mock(ItemContainer.class);
		when(client.getItemContainer(InventoryID.INVENTORY)).thenReturn(inv);
		when(inv.getItems()).thenReturn(new Item[]{new Item(ItemID.ABYSSAL_WHIP, 1)});

		List<ActivityReadiness.ReadinessWarning> warnings = ActivityReadiness.auditInventory(client, "Gargoyles");
		assertEquals(1, warnings.size());
		assertTrue(warnings.get(0).getTitle().contains("Rock Hammer"));
	}

	@Test
	public void testGargoyleNoWarningWhenHasHammer()
	{
		Client client = mock(Client.class);
		ItemContainer inv = mock(ItemContainer.class);
		when(client.getItemContainer(InventoryID.INVENTORY)).thenReturn(inv);
		when(inv.getItems()).thenReturn(new Item[]{new Item(ItemID.ROCK_HAMMER, 1)});

		List<ActivityReadiness.ReadinessWarning> warnings = ActivityReadiness.auditInventory(client, "Gargoyles");
		assertTrue(warnings.isEmpty());
	}

	@Test
	public void testDragonWarningWhenMissingDragonfireShieldOrPotion()
	{
		Client client = mock(Client.class);
		ItemContainer inv = mock(ItemContainer.class);
		when(client.getItemContainer(InventoryID.INVENTORY)).thenReturn(inv);
		when(inv.getItems()).thenReturn(new Item[]{new Item(ItemID.RUNE_SCIMITAR, 1)});

		List<ActivityReadiness.ReadinessWarning> warnings = ActivityReadiness.auditInventory(client, "Blue Dragons");
		assertEquals(1, warnings.size());
		assertTrue(warnings.get(0).getTitle().contains("Dragonfire Protection"));
	}

	@Test
	public void testDragonNoWarningWhenEquippingAntiDragonShield()
	{
		Client client = mock(Client.class);
		ItemContainer inv = mock(ItemContainer.class);
		ItemContainer equip = mock(ItemContainer.class);
		when(client.getItemContainer(InventoryID.INVENTORY)).thenReturn(inv);
		when(client.getItemContainer(InventoryID.EQUIPMENT)).thenReturn(equip);
		when(inv.getItems()).thenReturn(new Item[]{new Item(ItemID.RUNE_SCIMITAR, 1)});
		when(equip.getItems()).thenReturn(new Item[]{new Item(ItemID.ANTIDRAGON_SHIELD, 1)});

		List<ActivityReadiness.ReadinessWarning> warnings = ActivityReadiness.auditInventory(client, "Blue Dragons");
		assertTrue(warnings.isEmpty());
	}
}
