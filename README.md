# Circus of Plates Game

## Introduction
This project is a **Circus of Plates** game developed in Java using the **Swing** library. The objective of the game is to collect three or more plates in the clown’s stack to increase the player's score.  The game was designed with multiple **design patterns** to ensure flexibility and maintainability.

## Features
- **Dynamic Gameplay**: Players catch falling plates and other shapes using controlled objects to stack them.
- **Java Swing UI**: A responsive **Graphical User Interface (GUI)** created with **Swing**.
- **Game Engine**: Implements an engine to update frames and handle game logic efficiently.
- **Shape Variety**: Supports different **shapes** besides plates, dynamically loaded at runtime from a specific folder.
- **Scoring System**: Players gain points when collecting **three consecutive shapes of the same color**, even if they are different shapes.
- **Save and Load**: Uses **serialization** to save and restore game state.
- **Object-Oriented Design**: Uses encapsulation, inheritance, and polymorphism for modular design.
- **Design Patterns**:
  - **Singleton**: Manages game state.
  - **Factory / Object Pool**: Handles object creation and reuse efficiently.
  - **Iterator**: Allows structured traversal of collections.
  - **Dynamic Linkage**: Enables dynamic loading of game objects.
  - **Snapshot (Memento)**: Supports save and load functionality.
  - **State**: Manages different game states dynamically.
  - **Strategy**: Implements different behaviors dynamically.
  - **Observer**: Tracks game events and updates UI dynamically.
  - **MVC (Model-View-Controller)**: Organizes game structure efficiently.
  - **Object Pool**: Manages game objects efficiently to improve performance.

## Requirements
- Java (JDK 8 or later)
## How to Run
1. Compile the Java files:
   ```sh
   javac CircusGame.java
   ```
2. Run the game:
   ```sh
   java CircusGame
   ```
3. Control the character to **catch and stack plates and other shapes** while avoiding dropping them.

## Notes
- The game engine ensures **smooth frame updates** and **optimized performance**.
- Uses **dynamic object loading** for enhanced flexibility.
- Designed with **clean code** for readability and future extensions.
- Can be expanded with **new shapes, game mechanics, and animations**.

Enjoy the **Circus of Plates** challenge! 🎪
