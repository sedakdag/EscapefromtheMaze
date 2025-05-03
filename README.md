
<div align="center">

#  ✧ Escape from the Maze ✧

</div>


![Java](https://img.shields.io/badge/Language-Java-E6E6FA.svg)
![Data Structures](https://img.shields.io/badge/Data%20Structures-Stack%2C%20Queue%2C%20Linked%20List%2C%20Circular%20List-pink.svg)
![License](https://img.shields.io/badge/License-MIT-BA55D3.svg)
![Classes](https://img.shields.io/badge/Classes-MazeManager%20|%20Agent%20|%20TurnManager%20|%20MazeTile%20|%20GameController%20-ff69b4)


##  ⋆⭒˚𖠋𖠋𖠋*.⋆Contributors . ݁₊ ⊹ . ݁˖ . ݁


<table>
  <tr>
    <td align="center">
      <a href="https://github.com/yprq">
        <img src="https://avatars.githubusercontent.com/u/8271109?v=4" width="100px;" alt="yprq"/><br />
        <sub><b>Berçem Yaprak Aslan</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/dilarademrts">
        <img src="https://avatars.githubusercontent.com/u/904891144?v=4" width="100px;" alt="dilarademrts"/><br />
        <sub><b>Dilara Demirtaş</b></sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/sedakdag">
        <img src="https://avatars.githubusercontent.com/u/67658966?v=4" width="100px;" alt="sedakdag"/><br />
        <sub><b>Seda Akdağ</b></sub>
      </a>
    </td>
  </tr>
</table>







---

###  _✫ ✬ ✭ What is Our Project About?_ 

This Java project is a **multi-agent, turn-based and somewhat interactable maze simulation** that gets the information about  **_the width and the height of the maze, number of agents and how many turns there will be executed from the user!_**        
The goal is to help each agent reach the goal tile in a randomly generated maze filled with traps, walls, power-ups, and rotating corridors.

This Project was mainly made by using the data structures that was given to us in our course `CENG202 – Data Structures` which are **Stacks, Queues, Linked Lists, etc.**





---



## ⋆ 𐙚 ̊. How to Run the Project ?
1. Clone the repository
2. Compile with Java 17+
3. Run the main game loop
4. Watch agents navigate the maze!

### ᵎ!ᵎ _Requirements:_
- ⚠︎ Java 17 or higher
- ⇩ Any IDE (e.g., IntelliJ, Eclipse, NetBeans) or terminal

### _To compile and run in terminal:_

```bash
javac com/escape/maze/**/*.java
java com.escape.maze.Main
```


---
##  ⋆｡°✩ Features

-  ✦**Random Maze Generation** with guaranteed solvability !
-  ✦**Rotating Corridors** that shift positions during gameplay !
-  ✦**Multiple Agents** with turn-based movement system !
- ✦**Power-ups** and **Traps** that affect gameplay !
-  ✦**Detailed Turn Logging** with move history tracking !
-  ✦**Path-finding** with backtracking mechanics !

---

## ⋆˚࿔ Class and Component Structure

### 1. _MazeTile_
- Represents a single cell in the maze.
- Types: `W` = Wall, `T` = Trap, `P` = Power-Up, `G` = Goal, `E` = Empty.

### 2. _Agent_
- Stores the agent's ID, position, history, and stats.
- Key methods: `move()`, `backtrack()`, `applyPowerUp()`.

### 3. _MazeManager_
- Creates and manages the maze.
- Handles corridor rotation, move validation, and agent positioning.

### 4. _TurnManager_
- Manages turn order using a queue.
- Tracks round count, logs movements, and summarizes agent performance.


| _Symbol_ | _Meaning_       | _Description_                |
|--------|---------------|------------------------------|
| S      | Start         | Initial position for agents  |
| G      | Goal          | Destination to reach         |
| E      | Empty         | Walkable path                |
| W      | Wall          | Non-Traversable              |
| T      | Trap          | Forces agent to backtrack    |
| P      | Power-up      | Grants special abilities     |
| A      | Agent         | Player character in the maze |
---

## ⋆˚✿˖° Project Structure

````
com.escape.maze
├── manager
│ ├── MazeManager.java
│ └── TurnManager.java
├── model
│ ├── Agent.java
│ └── MazeTile.java
├── structures
│ ├── Stack.java
│ ├── Queue.java
│ ├── SinglyLinkedList.java
│ └── CircularLinkedList.java

````



## ִ ࣪𖤐 How It Works?

### ꩜ _Maze Generation:_
The `MazeManager` creates random mazes with:
- Up to 10% chance for Walls (`W`)
- Up to 20% chance for Traps (`T`)
- Up to 25% chance for Power-ups (`P`)
- ⁀➴ Guaranteed solvable path verified using BFS ⁺₊✧ 

### ⌖ _Agent Movement:_

Agents navigate the maze with:

- Turn-based movement system 
- Collision detection with walls
- Trap handling (forces backtrack)
- Power-up collection
```
//example java code
agent.move("RIGHT", maze.getGrid(), maze);
```

### ↺₊✧  _Rotating Corridors:_

Special rows rotate periodically, shifting all tiles:

- Affects agent positions !
- Updates maze state dynamically
- Managed via **CircularLinkedList**

```
//example java code
maze.rotateCorridor(3);
```
---

### .☘︎ ݁˖ _Turn Management:_


The TurnManager handles:

- Round-robin agent turns
- Movement logging
- Game state tracking

```
while(!turnManager.allAgentsFinished()) {
    Agent current = turnManager.getCurrentAgent();
    // ... make move ...
    turnManager.advanceTurn();
}
```
---
###  [◉¯] .𖥔 ݁ ˖ Maze Snapshot 

- With the help of the user _(by getting inputs such as the width and height of the maze, number of agents and how many turns there will be)_ we can upload a snapshot of the maze!
```
E T E W G
P E W T E
S E A E P
```
 
---
### 𓇢𓆸 Simulation Metrics

- After each simulation, the following metrics are displayed:

| Agent ID | Total Moves | Backtracks | Used Power-Up | Last 5 Moves (x,y) |
|----------|-------------|------------|----------------|--------------------|
| 1        | 12          | 1          | ✅             | (2,2) → (2,3) → ...|

Additional logs include:
- _Turn number_
- _Maze state_
- _First agent to reach the goal (winner)_
- _Agent action summaries_

---

### ⋆｡ﾟ☁︎｡⋆｡ ﾟ☾ ﾟ｡⋆ Example Output ⋆｡ﾟ☁︎｡⋆｡ ﾟ☾ ﾟ｡⋆
````
===== TURN 39 =====
Row 1 is rotated.

E E E A 
E S E G 
E W E E 
W E E W 

Agent 2 attempting to move DOWN from 3,0
Agent 2 has reached the GOAL!
Agent 2 moved from 3,0 to 3,1
========== Turn:39 ==========

>> AGENT INFORMATION <<
ID:2
Current Position:(3, 1)
Total Moves: 8
Power Up Active: Yes
Agent 2 has a Power-Up!
Total Backtracks: 0

>> LAST 5 MOVES <<
| Move No  | Position (x,y) |
|----------|----------------|
| 1        | (2,1)          |
| 2        | (2,1)          |
| 3        | (1,0)          |
| 4        | (2,0)          |
| 5        | (0,0)          |

Agent 2 has reached the GOAL!
````
---

##  License

This project is licensed under the MIT License.  
You can freely use, modify, distribute, and share it.

→ [Read full license here](https://opensource.org/licenses/MIT)

---

