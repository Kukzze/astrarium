package com.kukzze.astrarium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class FarmingPatches
{
	@Getter
	@RequiredArgsConstructor
	public static class HerbPatch
	{
		private final String location;
		private final String teleportMethod;
		private final boolean isDiseaseFree;
		private final String unlockRequirement;
		private final String diaryBonus;
	}

	private static final List<HerbPatch> PATCHES = new ArrayList<>();

	static
	{
		PATCHES.add(new HerbPatch(
			"Weiss",
			"Icy Basalt teleport (or Fairy Ring D-L-S)",
			true,
			"Making Friends with My Arm quest",
			"100% Disease-Free Patch! Guaranteed yield."
		));

		PATCHES.add(new HerbPatch(
			"Troll Stronghold",
			"Stony Basalt teleport (or climb from Burthorpe)",
			true,
			"My Arm's Big Adventure quest",
			"100% Disease-Free Patch! Guaranteed yield."
		));

		PATCHES.add(new HerbPatch(
			"Hosidius",
			"Xeric's Glade (Xeric's Talisman) or Tithe Farm minigame teleport",
			true,
			"50% Hosidius Favor (or Kourend general access)",
			"100% Disease-Free Patch! Guaranteed yield."
		));

		PATCHES.add(new HerbPatch(
			"Ardougne",
			"Ardougne Cloak 2+ (direct teleport to patch)",
			false,
			"None (Standard access)",
			"+10% extra harvest yield with Ardougne Medium+ Diary!"
		));

		PATCHES.add(new HerbPatch(
			"Catherby",
			"Camelot Teleport / Catherby Portal / Lunar Catherby Teleport",
			false,
			"None (Standard access)",
			"+15% extra yield with Kandarin Elite (or 10% with Hard) Diary!"
		));

		PATCHES.add(new HerbPatch(
			"Falador",
			"Explorer's Ring 2+ (cabbage patch teleport)",
			false,
			"None (Standard access)",
			"+10% extra harvest yield with Falador Medium+ Diary!"
		));

		PATCHES.add(new HerbPatch(
			"Port Phasmatys",
			"Ectophial (instant one-click teleport)",
			false,
			"Ghosts Ahoy quest",
			"Fastest teleport in game right next to patch."
		));

		PATCHES.add(new HerbPatch(
			"Farming Guild",
			"Skills Necklace (Farming Guild) or POH Spirit Tree",
			false,
			"65 Farming (requires access to West wing)",
			"+5% extra yield boost inside guild grounds with Anima seeds."
		));

		PATCHES.add(new HerbPatch(
			"Civitas illa Fortis (Varlamore)",
			"Quetzal Whistle / Civitas Teleport",
			false,
			"Children of the Sun / Varlamore access",
			"Located right next to the western market."
		));
	}

	public static List<HerbPatch> getPatches()
	{
		return Collections.unmodifiableList(PATCHES);
	}
}
