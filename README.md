# Typing Race Simulator

A Java object-oriented programming project that simulates a typing race between configurable competitors.

The project began as a console-based simulation and was later developed into a Java Swing application with configurable typists, race modifiers, statistics, historical results and leaderboard scoring.

---

## Project Structure

```text
TypingRaceSimulator/
├── Part1/
│   ├── Typist.java
│   ├── TypingRace.java
│   └── TypistTest.java
│
├── Part2/
│   ├── GUITypist.java
│   ├── RaceEngine.java
│   ├── RaceResult.java
│   ├── Leaderboard.java
│   └── TypingRaceGUI.java
│
├── Report.pdf
├── README.md
└── .gitignore
```

---

## Part 1 — Text Simulation

Part 1 implements the core typing race logic using Java classes.

Each `Typist` object stores its own state, including:

- name and symbol
- typing progress
- accuracy
- burnout state
- mistype state

The `TypingRace` class coordinates multiple typists and runs the race turn by turn until a winner completes the passage.

The simulation includes:

- character-by-character progress
- accuracy-based typing success
- mistypes that move a typist backwards
- burnout periods that temporarily prevent typing
- race resetting and winner detection

### Compile

```bash
cd Part1
javac *.java
```

### Run

```bash
java TypingRace
```

This runs a console-based typing race between multiple typists.

### Run Tests

```bash
java TypistTest
```

The test program checks:

- typing progress
- slide-back behaviour
- prevention of negative progress
- mistype state
- burnout countdown and recovery
- accuracy limits
- reset behaviour

The tests use automated pass/fail checks and do not require an external testing library.

---

## Part 2 — GUI Simulation

Part 2 develops the original typing race concept into a graphical application using Java Swing.

The GUI allows users to configure typists, select race modifiers, view the race in real time and compare results after each race.

### Features

- Typist customisation
  - name
  - symbol
  - progress colour
- Multiple typing styles
  - Touch Typist
  - Hunt & Peck
  - Phone Thumbs
  - Voice-to-Text
- Multiple keyboard types
  - Mechanical
  - Membrane
  - Touchscreen
  - Stenography
- Accessories that affect race behaviour
  - Wrist Support
  - Energy Drink
  - Noise-Cancelling Headphones
- Race modifiers
  - Autocorrect
  - Caffeine Mode
  - Night Shift
- Predefined and custom typing passages
- Configurable number of typists
- Real-time race display
- Progress bars
- WPM calculation
- Accuracy statistics
- Burnout statistics
- Race history
- Leaderboard scoring
- Performance comparison view

### Compile

```bash
cd Part2
javac *.java
```

### Run

```bash
java TypingRaceGUI
```

---

## Object-Oriented Design

The project demonstrates several object-oriented programming concepts.

### Encapsulation

Typist state is stored in private fields and accessed or modified through methods.

For example, the `Typist` class controls its own accuracy value and prevents it from going below `0.0` or above `1.0`.

This keeps object state protected and ensures that invalid values are not stored.

### Classes and Objects

The project is divided into classes with different responsibilities, including:

- `Typist`
- `TypingRace`
- `GUITypist`
- `RaceEngine`
- `RaceResult`
- `Leaderboard`
- `TypingRaceGUI`

Objects from these classes work together to run and display the race.

### Separation of Responsibilities

The GUI and race simulation logic are separated into different classes.

`TypingRaceGUI` is mainly responsible for:

- creating and managing Swing components
- accepting user input
- displaying race progress
- displaying statistics
- displaying race history
- displaying leaderboard information

`RaceEngine` is responsible for:

- advancing race turns
- calculating typing success
- calculating mistype probability
- applying slide-back behaviour
- calculating burnout probability
- applying race modifiers such as autocorrect and caffeine mode
- applying accessory effects

This keeps the simulation rules separate from the presentation layer.

### Composition

The project uses composition and object relationships.

For example:

- a `GUITypist` stores a collection of `RaceResult` objects
- `TypingRaceGUI` uses `GUITypist` objects
- `TypingRaceGUI` uses a `RaceEngine`
- `TypingRaceGUI` uses a `Leaderboard`

This allows different parts of the program to manage their own responsibilities.

### Inheritance

`TypingRaceGUI` extends Java Swing's `JFrame`.

This allows the class to inherit standard window behaviour while adding application-specific functionality for the typing race simulator.

---

## Architecture

The GUI version is structured so that presentation, simulation logic and data are kept separate.

```text
TypingRaceGUI
     |
     | uses
     v
 RaceEngine
     |
     | updates
     v
 GUITypist
     |
     | stores
     v
 RaceResult

TypingRaceGUI
     |
     | uses
     v
 Leaderboard
```

### Class Responsibilities

#### `TypingRaceGUI`

Handles:

- Swing components
- user input
- race display
- statistics display
- race history
- comparison views

#### `RaceEngine`

Handles:

- race simulation rules
- typist progression
- mistypes
- burnout
- race modifiers
- accessory effects

#### `GUITypist`

Stores:

- typist configuration
- current race progress
- current accuracy
- typing statistics
- burnout statistics
- previous race results

#### `RaceResult`

Stores information about a completed race, including:

- finishing position
- WPM
- accuracy percentage
- burnout count
- accuracy before and after the race

#### `Leaderboard`

Handles:

- race points
- wins
- clean races
- badge assignment
- leaderboard output

---

## Testing

Part 1 includes a lightweight automated test program for the `Typist` class.

The tests cover:

- character progression
- progress lower bounds
- mistype state
- burnout state
- burnout recovery
- accuracy upper bounds
- accuracy lower bounds
- reset behaviour

The test runner reports pass and fail results automatically.

No external testing framework is required.

---

## Technologies

- Java
- Java Swing
- Java Collections Framework
- Event-driven programming
- Object-oriented programming

---

## Dependencies

- Java Development Kit (JDK 11 or higher)
- Standard Java libraries
- Java Swing
- No external libraries required

---

## Background

This project was originally developed as coursework for ECS414U.

After submission, the project was further refactored and improved as a portfolio project.

Post-submission improvements include:

- separating race simulation logic from the Swing GUI using `RaceEngine`
- improving separation of concerns
- improving automated behavioural tests
- improving project documentation
- documenting the application architecture

---

## Notes

- `.class` files are excluded using `.gitignore`.
- Part 2 develops the concepts introduced in Part 1 into a larger GUI-based simulation.
- The GUI version uses Java Swing and standard Java libraries only.

---

## Author

Umer Liaquat
