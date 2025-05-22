# Minesweeper Game (Java Console Version)

## Description

This is a console-based Minesweeper game implemented in Java. It allows the player to interact with a grid-based minefield by selecting coordinates (e.g., `A1`) to reveal cells. The game ends when either all safe cells are revealed (win) or a mine is revealed (loss).

The application supports a customizable board size and dynamically places mines (up to 35% of the total cells). Recursive reveal is implemented for cells with 0 adjacent mines.

---

## Features

- Configurable grid size (NxN)
- Mine limit restricted to 35% of total cells
- Input-based cell selection (e.g., `B2`, `A0`)
- Recursive revealing for zero-adjacent-mine cells
- Win condition check
- Game over if mine is revealed
- JUnit tests for core game logic

---

## Assumptions & Design

- The board uses a coordinate system: rows labeled A-Z, columns as numbers (0-based index).
- Mines are placed randomly at the start of the game.
- The board is updated with mine counts for each non-mine cell.
- The first move may land on a mine (no first-move immunity).
- Game logic and UI are combined in a single class (`MineSweeper`) for simplicity, but could be separated for larger projects.

---

## Requirements

- Java 8 or newer
- VS Code or any Java-supporting IDE
- macOS, Windows, or Linux
- JUnit 4.13.2 (for testing)

---

### To Compile:
javac MineSweeper.java

### To Run:
java MineSweeper

### To Run Tests:
- Compile:
  javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar TestMineSweeper.java
- Run:
  java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestMineSweeper
