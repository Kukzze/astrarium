# Astrarium 🧭✨

[![License](https://img.shields.io/badge/license-BSD_2--Clause-blue.svg)](LICENSE)
[![RuneLite](https://img.shields.io/badge/RuneLite-Plugin%20Hub-orange.svg)](https://runelite.net/plugin-hub)

I built **Astrarium** because I was sick of constantly alt-tabbing between three different wiki tabs, combat level calculators, and skilling spreadsheets while forgetting my rock hammer on gargoyle tasks for the tenth time :D

Astrarium turns your RuneLite sidebar into an all-in-one companion that tracks your exact combat math, guides you through the optimal quest path and skilling routes, audits your gear before you head out on slayer tasks, and logs your session achievements with custom atmospheric themes.

---

## 🌟 What's Inside

### ⚔️ 1. Live Combat Level Mathematics
Ever wonder whether 2 Strength levels or 1 Prayer level will bump your combat level?
- Shows your exact decimal progress toward the next combat level (e.g. `91.50 -> 92`).
- Dynamically calculates the shortest path to your next combat level across all combat skills (`+2 Str`, `+2 Att`, `+2 Def`, `+3 Pray`, etc.).
- Cleanly detects when you hit max combat (126) so you don't get messy negative numbers.

### 🗺️ 2. Optimal Quest Route
Early quests in Old School RuneScape can jumpstart an account past hundreds of hours of early grinding:
- Tracks the most impactful early-to-endgame quests in optimal order (Waterfall Quest, Witch's House, Knight's Sword, Fight Arena, Grand Tree, Fairy Rings, RFD, DS2, SOTE, DT2).
- Automatically checks your quest states and skill levels:
  - ✅ **Completed**
  - ▶️ **In Progress**
  - 🔓 **Ready to Start** (you meet all skill requirements)
  - 🔒 **Needs Requirements** (shows exact missing skill levels)
- Highlights key XP rewards and utility unlocks so you always know what to do next.

### ⚡ 3. Dynamic Optimal Leveling Guide
Forget searching through 10-page wiki skilling articles for your level bracket:
- Select any skill (Agility, Thieving, Construction, Prayer, Hunter, Magic, Ranged, Melee, Herblore, Farming, Woodcutting, Fishing, Cooking, Mining, Smithing).
- Instantly displays your **current best training method**, expected XP/hour rate, exact location, and gear tips.
- Displays a **Next Unlock** card showing what level and method you unlock next so you always have a short-term goal in sight.

### 🧭 4. Milestone Progression Radar
Track your journey across four distinct eras of account progression:
- **Early Game**: Protection Prayers (43 Prayer), Fairy Rings Access, Graceful Outfit readiness.
- **Mid Game**: Barrows Gloves (RFD stats), Piety (70 Def/Pray), Fire Cape stats, Dragon Slayer II stat checklist.
- **Late Game**: Song of the Elves (70 base stats), Maxed POH pool & nexus (83 Construction), Abyssal Whip/Trident Slayer tiers.
- **Endgame**: High Raids & Colosseum readiness, Max Cape (99 all stats).

### 🎒 5. Activity & Slayer Gear Readiness
Never arrive at a slayer task or boss fight missing a crucial item again. Astrarium audits both your inventory and equipped gear in real-time:
- **Gargoyles & Grotesque Guardians**: Warns if you forgot your Rock Hammer or Granite Hammer.
- **Kurasks & Turoths**: Warns if you're missing a Leaf-Bladed weapon, Broad arrows/bolts, or Slayer's dart.
- **Dragons & Vorkath**: Warns if you forgot an Anti-dragon shield, Dragonfire shield, or Antifire potion (saves you from 50+ dragonfire hits!).
- **Aberrant Spectres**: Warns if you're missing a Nosepeg or Slayer helmet.
- **Dust Devils & Smoke Devils**: Warns if you're missing a Facemask or Slayer helmet.
- **Banshees**: Warns if you're missing Earmuffs or Slayer helmet.
- **Basilisks**: Warns if you're missing a Mirror Shield or V's Shield.
- **Cave Horrors**: Warns if you forgot your Witchwood Icon.
- **Wyverns**: Warns if you forgot an Elemental, Mind, or Ancient Wyvern Shield.
- **Desert Lizards**: Warns if you forgot Ice Coolers.

### 📜 6. Session Adventure Log ("Captain's Log")
A live timeline that records what you achieve while playing:
- 🌟 Skill level-ups and total XP milestones.
- ⚔️ Boss killcounts, raid completions, and personal bests (CoX, ToB, ToA, Gauntlet, Wintertodt, Tempoross, etc.).
- 📜 Clue scroll completions across all tiers.
- 📖 New collection log unique item unlocks.
- 🏆 Quest completions & combat achievement tasks.

### 🎨 7. Atmospheric Lore Themes
Tired of the plain gray client background? Pick from custom palettes inspired by Gielinor lore:
- **Sunfire Gold**: The warm golden radiance of Varlamore and the Fortis Colosseum.
- **Zarosian Void**: Deep mystic purples from the Ancient Magicks.
- **Prifddinas Crystal**: Glowing cyan from the elven crystal city.
- **Morytania Blood**: Dark crimson inspired by Slepe and the Theatre of Blood.
- **Runic Slate**: Celestial sapphire and astral rune hues.

---

## 🛡️ Jagex Third-Party Client Compliance

Astrarium is 100% compliant with Jagex's Third-Party Client Guidelines:
- ❌ No boss attack, tick, or mechanic countdown timers.
- ❌ No automated clicks, prayer flicking, or gear switching.
- ❌ No hidden game data or unfair advantages.
- ✅ Purely client-side UI, local player calculations, and visual theme styling.

---

## 🛠️ Building & Running Locally

Astrarium is built using Java 11+ and Gradle.

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
