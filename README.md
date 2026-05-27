🐍 Dnake (Terminal Snake Game)

Dnake is a modern terminal-based Snake game developed in Java using clean Object-Oriented Programming (OOP) principles, design patterns, and a modular package architecture.

The project is designed to demonstrate advanced Java concepts such as:

* Generics
* Interfaces
* Polymorphism
* Anonymous Inner Classes
* Modular Architecture

instead of using messy “spaghetti code”.

⸻

🛠️ Features

* Clean Project Structure using the standard src/main/java architecture
* Wrap-around Map System (snake can pass through walls)
* Generic Entity Spawner for safely spawning objects on the map
* Bounded Generics for advanced collision checks
* Dynamic Game Loop
* Poison System with random spawn chance
* Real-time Score Tracking

⸻

📂 Project Structure

SnakeGame/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── dnake/
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
├── .classpath
└── .gitignore

⸻

🚀 How to Run

Open a terminal inside the project root folder (SnakeGame/) and run the following commands.

⸻

1️⃣ Compile

javac -d bin -sourcepath src/main/java src/main/java/com/dnake/main/Main.java

⸻

2️⃣ Run

java -cp bin com.dnake.main.Main

⸻

🎮 Controls

Key	Action
w	Move Up
a	Move Left
s	Move Down
d	Move Right
q	Quit Game

⸻

📜 Game Rules

* 0 = Food
    Eating food gives +10 score and increases snake length.
* ? = Poison
    Every time the snake eats food, poison has a 20% chance to appear randomly on the map.
* Eating poison causes GAME OVER.
* Hitting your own tail also ends the game.
* The map uses a wrap-around system.
    Leaving one side of the map makes the snake appear on the opposite side.

⸻

🧠 Technologies & Concepts Used

This project was created to practice advanced Java architecture and software design concepts.

Used concepts:

* Object-Oriented Programming (OOP)
* Encapsulation
* Inheritance
* Polymorphism
* Abstract Classes
* Interfaces
* Generics
* Bounded Generics
* Anonymous Inner Classes
* Factory Pattern
* Modular Package Architecture
* Game Loop Architecture

⸻

📌 Possible Future Features

* Multiple Poison Types
* Different Food Types
* Save Score System
* High Score Board
* Real-time Keyboard Input
* Difficulty Levels
* ANSI Color Support
* Multiplayer Mode
* Sound Effects

⸻

👨‍💻 Developer Note

Dnake was designed as more than just a simple Snake clone.

The main goals of the project are:

* writing clean and maintainable code,
* practicing OOP principles,
* improving Java architecture skills,
* and building scalable project structures.