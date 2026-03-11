# Risk Game in Java

## Overview

This project is a Java-based implementation of the classic board game Risk. Risk is a strategy game where players aim to conquer the world by controlling armies and engaging in battles. This implementation focuses on providing a robust and interactive command-line version of the game, allowing players to experience the strategic depth and excitement of Risk.

## Table of Contents

1. [Features](#features)
2. [Installation](#installation)
3. [Usage](#usage)
4. [Game Rules](#game-rules)
5. [Architecture](#architecture)
6. [Contributing](#contributing)
7. [License](#license)

## Features

- **Turn-based Gameplay**: Players take turns to deploy armies, attack territories, and fortify positions.
- **AI Opponents**: Play against computer-controlled opponents with varying levels of difficulty.
- **Save/Load Game**: Save your progress and load previous games.
- **Command-line Interface**: Intuitive CLI for interacting with the game.
- **Detailed Logging**: Game events are logged for review and debugging.

## Installation

1. **Clone the repository**:
    ```sh
    git clone https://github.com/gursimran2407/Risk_Game_Java.git
    cd Risk_Game_Java
    ```

2. **Compile the project**:
    ```sh
    javac -d bin src/*.java
    ```

3. **Run the game**:
    ```sh
    java -cp bin Main
    ```

## Usage

1. **Starting a New Game**:
    - Run the `Main` class.
    - Follow the on-screen prompts to set up the game, including the number of players and their types (human or AI).

2. **Gameplay**:
    - Players take turns to perform actions: deploy armies, attack territories, and fortify positions.
    - Enter commands as prompted by the CLI.

3. **Saving and Loading**:
    - Save the game state using the `save` command.
    - Load a saved game using the `load` command followed by the filename.

## Game Rules

- **Objective**: Conquer all territories on the map or achieve specific mission objectives.
- **Setup**: Players start with a set number of armies and territories.
- **Turn Phases**:
    1. **Reinforcement**: Deploy new armies to controlled territories.
    2. **Attack**: Engage in battles to conquer new territories.
    3. **Fortification**: Move armies between controlled territories to strengthen positions.
- **Combat**: Dice rolls determine the outcome of battles, with attackers and defenders rolling dice based on their army sizes.

## Architecture

- **Main Class**: Entry point of the game.
- **Game Engine**: Manages game state, player turns, and game logic.
- **Player Class**: Represents a player (human or AI).
- **Territory Class**: Represents a territory on the map.
- **Command Parser**: Interprets and executes player commands.
- **Logger**: Logs game events for review.

## Contributing

1. **Fork the repository**:
    Click the 'Fork' button on the top right corner of this page.

2. **Create a new branch**:
    ```sh
    git checkout -b feature-branch
    ```

3. **Make your changes**:
    - Ensure your code follows the project's coding standards.
    - Include appropriate tests.

4. **Commit your changes**:
    ```sh
    git commit -m "Description of your changes"
    ```

5. **Push to your branch**:
    ```sh
    git push origin feature-branch
    ```

6. **Create a Pull Request**:
    Submit your pull request from your fork's branch to the main repository's `master` branch.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
