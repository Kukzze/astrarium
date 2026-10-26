# Astrarium 🧭✨

[![License](https://img.shields.io/badge/license-BSD_2--Clause-blue.svg)](LICENSE)
[![RuneLite](https://img.shields.io/badge/RuneLite-Plugin%20Hub-orange.svg)](https://runelite.net/plugin-hub)

I built **Astrarium** because I was sick of constantly alt-tabbing between three different wiki tabs, combat level calculators, quest guides, and skilling spreadsheets while forgetting my rock hammer on gargoyle tasks for the tenth time :D

Astrarium turns your RuneLite sidebar into an all-in-one companion for every stage of your adventure:
- ⚔️ **Live Combat Formula Math**: Real-time decimal progress bar + exact levels needed across Attack, Strength, Defence, HP, Prayer, Ranged, or Magic to reach the next combat level.
- 🗺️ **Optimal Quest Route**: High-impact XP quests and major milestones tracked in the community's optimal order (Waterfall, Witch's House, Knight's Sword, RFD, DS2, SOTE, DT2), showing what you're ready to start right now based on your skill levels.
- ⚡ **Dynamic Leveling Guide**: Select any skill and instantly see the active community-standard training method for your exact level, expected XP/hr, location, and the next unlock.
- 📜 **Achievement Diary Roadmap**: All 12 regions with stat eligibility checks and game-changing perk highlights (Ardougne Cloak teleports, Barrows 50% runes & Bonecrusher, Camelot bank teleport).
- ⚔️ **BiS Gear Progression Ladder**: Step-by-step tiered equipment upgrades for Melee, Ranged, and Magic with slot breakdowns and unlock sources.
- 🐲 **Boss Intel & Strategy Cards**: Tactical cheat-sheets for Zulrah, Vorkath, The Corrupted Gauntlet (CG), Muspah, ToA, CoX, ToB, and Grotesque Guardians detailing overhead prayers, essentials, and mechanics.
- 🎒 **Activity & Slayer Gear Audit**: Live inventory & equipment checks so you never forget Rock Hammers, Leaf-Bladed weapons, Anti-dragon shields/Antifires, Slayer helms/Nosepegs, Earmuffs, Mirror Shields, Ice Coolers, Witchwood Icons, or Wyvern shields.
- 🌾 **Herb Patch Navigator**: Directory of all 9 Herb patches across Gielinor with fastest teleports, disease-free patch flags (Weiss, Troll Stronghold, Hosidius), and diary yield bonuses.
- ☀️ **Dailies & Resets Tracker**: One-click checklist for daily money/XP activities (Zaff's discounted battlestaves, NMZ herb boxes, Cromperty essence, Bert sand, and weekly Tears of Guthix).
- 🌐 **Transportation & Spellbook Matrix**: Visual unlock tracker for Fairy Rings, Spirit Trees, Magic Mushtrees, Quetzal transport, Ancient Magicks, Lunar, Arceuus, Piety, Rigour, and Augury.
- 🛡️ **Wilderness Survival Radar**: Teleport thresholds (Level 20 vs Level 30 with seed pod), skull prevention safety, escape gear essentials, and boss locations.
- 📊 **Session Adventure Log & Efficiency Tracker**: Tracks time played, net XP gained, XP/hr, bosses defeated, clues solved, unique collection logs, and lets you dispatch a rich Discord session summary card.
- 🎨 **Atmospheric Lore Themes**: Sunfire Gold (Varlamore), Zarosian Void (Ancient Magicks), Prifddinas Crystal (Elven), Morytania Blood (Vampyric/ToB), and Runic Slate (Astral).

---

## 🛡️ Jagex Third-Party Client Compliance

Astrarium is 100% compliant with Jagex's Third-Party Client Guidelines:
- ❌ **No mechanic countdowns or boss tick timers**: Purely informative UI.
- ❌ **No automated actions**: Zero clicks, zero prayer flicking, zero gear switching.
- ❌ **No network scraping or hidden game data**: Purely client-side UI, local player calculations, and visual theme styling.

---

## 🛠️ Building & Running Locally

Astrarium is built using Java 11+ and standard Gradle (`build=standard`).

```bash
# Clone the repository
git clone https://github.com/Kukzze/astrarium.git
cd astrarium

# Run all unit tests
./gradlew test

# Launch RuneLite with Astrarium loaded locally
./gradlew run
```

---

## 📄 License

Astrarium is open-source software licensed under the [BSD 2-Clause License](LICENSE).
Created with ❤️ by **Kukzze**.
