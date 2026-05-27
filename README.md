# 🐍 Dnake (Terminal Snake Game)

Dnake is a modern terminal-based Snake game developed in Java using clean Object-Oriented Programming (OOP) principles, design patterns, and a modular package architecture.

The project focuses on writing scalable and maintainable code instead of messy “spaghetti code”.

---

# ✨ Features

- Clean `src/main/java` project architecture
- Dynamic game loop
- Wrap-around map system
- Generic entity spawning system
- Poison mechanics with random spawn chance
- Real-time score tracking
- Collision detection system
- Modular and scalable package structure

---

# 🧠 Java Concepts Used

This project was built to practice advanced Java and software architecture concepts.

- Object-Oriented Programming (OOP)
- Encapsulation
- Inheritance
- Polymorphism
- Abstract Classes
- Interfaces
- Generics
- Bounded Generics
- Anonymous Inner Classes
- Factory Pattern
- Modular Architecture

---

# 📂 Project Structure

```text
SnakeGame/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── dnake/
│
│                   ├── main/
│                   │   └── Main.java
│                   │
│                   ├── engine/
│                   │   └── Map.java
│                   │
│                   ├── entities/
│                   │   ├── GameObject.java
│                   │   ├── Snake.java
│                   │   ├── SnakeLocation.java
│                   │   ├── Food.java
│                   │   └── Poison.java
│                   │
│                   ├── interfaces/
│                   │   ├── IConsumable.java
│                   │   └── Spawnable.java
│                   │
│                   └── utils/
│                       └── EntitySpawner.java
│
├── bin/
├── .vscode/
└── .gitignore
```

---

# 🚀 How to Run

Open a terminal inside the project root directory (`SnakeGame/`).

## 1️⃣ Compile

```bash
javac -d bin -sourcepath src/main/java src/main/java/com/dnake/main/Main.java
```

## 2️⃣ Run

```bash
java -cp bin com.dnake.main.Main
```

---

# 🎮 Controls

| Key | Action |
|-----|--------|
| `w` | Move Up |
| `a` | Move Left |
| `s` | Move Down |
| `d` | Move Right |
| `q` | Quit Game |

---

# 📜 Game Rules

- `0` = Food  
  Eating food gives **+10 score** and increases snake length.

- `?` = Poison  
  Every time the snake eats food, poison has a **20% chance** to spawn randomly.

- Eating poison causes **GAME OVER**.

- Hitting the snake’s own tail also causes **GAME OVER**.

- The game uses a **wrap-around map system**.  
  Leaving one side of the map makes the snake appear on the opposite side.

---

# 📌 Possible Future Features

- Multiple poison types
- Different food types
- Save/load score system
- High score leaderboard
- Real-time keyboard input
- Difficulty levels
- ANSI color support
- Multiplayer mode
- Sound effects

---

# 👨‍💻 Developer Note

Dnake was designed as more than just a simple Snake clone.

The main goals of the project are:

- Writing clean and maintainable Java code
- Practicing advanced OOP principles
- Improving software architecture skills
- Building scalable project structures
- Learning professional package organization