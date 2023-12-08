## Hello and Welcome!
Thank you for checking out my Kalah game implementation! Below is a guide to get you started:

## Table of contents

- [Quick start](#quick-start)
- [Architecture](#arhitecture)
- [Tech stack](#tech-stack)
- [Project Strucure](#project-structure)
- [Implementation](#implementation)

## Quick start

Ensure that you have the following tools installed on your machine:

- Docker
- Docker Compose
###  How to Run the game


1. Clone the repository:

   ```bash
   git clone https://github.com/nadiravda/Kalah.git
2. cd Kalah(Kalah-main)
3. docker-compose up --build
4. find the game on your localhost:4200

    - Note: It may take a bit longer because target file is included with the latest snapshot, but you can also run mvn clean install before these commands to get a new one locally.

## Tech stack

###  Backend:
- **Framework:**
    - Spring Boot
        - Version: 3.2.0
        - Utilized Spring Boot Initializer for project setup

- **Programming Language:**

    - Java 17

- **IDE (Integrated Development Environment):**

    - IntelliJ IDEA

- **Additional Information:**
    - Dependencies managed via the project's `pom.xml` (Maven configuration) to view utilized libraries and tools.
###  Frontend:
- **Framework:**
    - Angular CLI
        - Version: 17.0.5
        - Node: 19.6.0
        - Package Manager: npm 9.4.0


- **IDE (Integrated Development Environment):**

    - Visual Studio Code

## Project structure

- Main project structure, just for reference.


```text
    └── main
        ├─ java
        │   └── com.kalahbackend
        │       ├── controller
        │       ├── exception
        │       ├── kalahGameEngine
        │       ├── model
        │       ├── security
        │       ├── service
        │       └── KalahBackendApplication
        └── resources
    └── test
        ├─ java
            └── com.kalahbackend
                 ├── KalahControllerTests
```

```text
    └── kalah-frontend
        ├─ src
        │   └── app
        │       ├── board
        │       ├── field
        │       ├── models
        │       ├── new-game-dialog
        │       ├── winner-dialog
        │       ├── game.service.ts
        │       └── NG-Files
```

## Implementation

- **Implementation Details:**
    - This project features a straightforward implementation of the classic Kalah game, translated into a point-and-click interface with a single-page design. The interface includes two players and provides options for starting a new game and displaying game results.
    - Communication between the frontend and backend is facilitated through three endpoints. The first endpoint is triggered when a player makes a move, the second one updates the game state, and the third initiates the start of a new game.
    - While the frontend remains structured for the classic Kalah game, backend allows for the integration of different game engines. By changing the backend engine and adjusting the frontend, one can explore variations of the game with additional fields or a distinct set of rules. 
  




