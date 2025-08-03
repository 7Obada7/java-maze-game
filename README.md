# Maze Adventure Game 🧩 (Console-Based)

A simple text-based maze exploration game developed in Java.  
This project was created during the **early years of my university studies** as part of an assignment, and focuses on basic game mechanics, conditional logic, and grid navigation.

---

## 🎮 Gameplay Overview

You start in a maze filled with:
- 🧱 Walls (`#`)
- 💥 Mines (`!`)
- 🏁 Bonuses (`T`, `R`, `H`, `F`)
- 🟢 Start (`B`)
- 🔚 Finish (`E`)

Your objective is to move from the **start point (B)** to the **end point (E)** while:
- Avoiding mines and walls
- Collecting and using bonuses
- Making the fewest moves possible

---

## 🧠 Features

- Move in 4 directions: `W`, `A`, `S`, `D`
- Use a bonus by entering `+`
- Bonuses:
  - `T`: Teleport to a coordinate
  - `R`: Remove a wall
  - `H`: Reduce move count
  - `F`: Defuse a mine
- Random movement of bonuses and mines every few turns
- Maze updates with each move — all rendered in the terminal
- Player represented as `*`

---

## 🚀 How to Run

> Requires Java installed and configured on your system.

1. Clone or download the code
2. Compile and run:

```bash
javac Main.java Player.java
java Main
