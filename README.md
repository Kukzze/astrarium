# Chronicle 📜✨

[![License](https://img.shields.io/badge/license-BSD_2--Clause-blue.svg)](LICENSE)
[![RuneLite Plugin](https://img.shields.io/badge/RuneLite-Plugin%20Hub-orange.svg)](https://runelite.net/plugin-hub)

**Chronicle** is the ultimate all-in-one player command center for Old School RuneScape. Designed for every adventurer—from fresh Lumbridge spawns to endgame raiders—Chronicle combines atmospheric theme customization, live combat mathematics, progression milestone radars, session story logging, and inventory readiness checks into a sleek, immersive sidebar dashboard.

---

## 🌟 Key Features

### 🎨 Atmospheric Themes
Personalize your dashboard with custom themes inspired by iconic Gielinor lore:
- **Sunfire Gold**: The radiant warmth of Varlamore and the Colosseum.
- **Zarosian Void**: Deep purple and mystic energy from the Ancient Magicks.
- **Prifddinas Crystal**: Glowing cyan and elven crystal resonance.
- **Morytania Blood**: Dark crimson inspired by Slepe and the Theatre of Blood.
- **Runic Slate**: Celestial sapphire and astral rune shades.

### ⚔️ Live Combat Level Mathematics
Wondering how close you are to your next combat level?
- Dynamic progress bar showing your exact decimal progress toward the next combat level (e.g. `91.50 -> 92`).
- Live calculation of exactly how many levels you need in **Attack**, **Strength**, **Defence**, **Hitpoints**, **Prayer**, **Ranged**, or **Magic** to level up your combat.
- No more guessing whether 2 levels of strength or 1 prayer level will give you that next combat level!

### 🧭 Milestone Progression Radar
Track your journey across 4 distinct eras of progression:
- **Early Game**: Protection Prayers (43 Prayer), Fairy Rings Access, Graceful Outfit readiness.
- **Mid Game**: Barrows Gloves (RFD requirements), Piety (70 Def/Pray), Fire Cape stats, Dragon Slayer II stat requirements.
- **Late Game**: Song of the Elves (70 base stats), Maxed POH pool & nexus (83 Construction), Abyssal Whip/Trident Slayer tiers.
- **Endgame**: High Raids & Colosseum readiness, Max Cape (99 all stats).
- Visual progress bars show your exact percentage completion toward each milestone.

### 📜 Session Story ("Captain's Log")
A live timeline that records your accomplishments as you play:
- Level-up milestones and total experience gained.
- Boss killcounts and personal bests.
- Clue scroll completions across all tiers.
- New slayer task assignments and major unlocks.

### 🎒 Activity & Slayer Readiness Audit
Never arrive at a slayer task unprepared again:
- **Gargoyles**: Warns if you forgot your Rock Hammer or Granite Hammer.
- **Kurasks & Turoths**: Warns if you don't have a leaf-bladed weapon, broad arrows, broad bolts, or slayer dart.
- **Aberrant Spectres**: Checks for a Nosepeg or Slayer helmet.
- **Dust & Smoke Devils**: Checks for a Facemask or Slayer helmet.
- **Banshees**: Checks for Earmuffs or Slayer helmet.
- **Basilisks**: Checks for a Mirror Shield or V's Shield.
- **Desert Lizards**: Checks for Ice Coolers.

---

## 🛡️ Jagex Compliance

Chronicle strictly adheres to all **Jagex Third-Party Client Guidelines**:
- ❌ No boss attack or tick countdown timers.
- ❌ No automated clicks, prayers, or gear switching.
- ❌ No hidden info or unfair game advantage.
- ✅ Purely client-side UI dashboard, visual themes, and local player statistics.

---

## 🛠️ Development & Building

Chronicle is built with Java 11+ and Gradle.

```bash
# Build the project
./gradlew build

# Run unit tests
./gradlew test

# Launch RuneLite with Chronicle loaded locally
./gradlew run
```

---

## 📄 License

Chronicle is open-source software licensed under the [BSD 2-Clause License](LICENSE).
Created with ❤️ by **Kukzze**.
