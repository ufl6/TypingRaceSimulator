# Typing Race Simulator

Object Oriented Programming Project — ECS414U

---

## Project Structure

```
TypingRaceSimulator/
├── Part1/    # Text-based simulation
├── Part2/    # GUI-based simulation
├── Report.pdf
├── README.md
└── .gitignore
```

---

## Part 1 — Text Simulation

Part 1 implements the core typing race logic using Java classes.

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

---

## Part 2 — GUI Simulation

Part 2 extends the project with a graphical user interface using Java Swing.

### Features

* Typist customisation (name, symbol, colour)
* Typing styles, keyboard types, and accessories
* Race modifiers (autocorrect, caffeine mode, night shift)
* Real-time race display with progress bars
* Statistics (WPM, accuracy, burnouts)
* Leaderboard and race history
* Comparison view between typists

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

## Dependencies

* Java Development Kit (JDK 11 or higher)
* Uses standard Java libraries (Swing for GUI)
* No external libraries required

---

## Notes

* `.class` files are excluded using `.gitignore`.
* The GUI version builds on the logic developed in Part 1.

---

## Author

Umer Liaquat
