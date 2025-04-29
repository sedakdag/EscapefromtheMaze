package com.escape.maze.simulation;

import com.escape.maze.structures.Stack;
import com.escape.maze.structures.Queue;
import com.escape.maze.manager.MazeManager;
import com.escape.maze.manager.TurnManager;
import com.escape.maze.model.Agent;
import com.escape.maze.model.MazeTile;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GameController {
	private MazeManager maze;
	private TurnManager turns;
	private int maxTurns;
	private int turnCount;

	private Random rand;

	public GameController(MazeManager maze, TurnManager turns, int maxTurns, int numAgents) {
		this.maze = maze;
		this.turns = turns;
		this.maxTurns = maxTurns;
		this.turnCount = 0;
		this.rand = new Random();

		initializeGame(numAgents);
	}

	public void initializeGame(int numAgents) {
		maze.generateMaze();

		for (int i = 0; i < numAgents; i++) {
			int startX = maze.getStartX();
			int startY = maze.getStartY();
			Agent agent = new Agent(i + 1, startX, startY, new Stack<>(), false, 0, 0, false);
			turns.add(agent);
			maze.updateAgentLocation(agent, startX, startY);
		}

		System.out.println("\nGame Initialized with " + numAgents + " agents.");
		maze.printMazeSnapshot();
	}

	public void runSimulation() {
		while(!turns.allAgentsFinished() && turnCount < maxTurns) {
			System.out.println("\n===== TURN " + (turnCount + 1) + " =====");

			maze.rotateNextCorridor();
			maze.printMazeSnapshot();

			Agent currentAgent = turns.getCurrentAgent();

			if(currentAgent != null && !currentAgent.isHasReachedGoal()) {
				processAgentAction(currentAgent);
			}

			turns.advanceTurn();

			turns.logTurnSummary(currentAgent);

			turnCount++;
		}

		printFinalStatistics();
	}

	public void processAgentAction(Agent a) {
		Random rand = new Random();
		String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
		String direction = directions[rand.nextInt(directions.length)];

		int oldX = a.getCurrentX();
		int oldY = a.getCurrentY();

		if (maze.isValidMove(oldX, oldY, direction)) {
			a.move(direction, maze.getGrid(), maze);

			MazeTile currentTile = maze.getTile(a.getCurrentX(), a.getCurrentY());
			if (currentTile == null) return;

			if (currentTile.getType() == 'T') {
				a.backtrack();
				System.out.println("Agent " + a.getId() + " triggered a TRAP and backtracked!");
			} else if (currentTile.getType() == 'P') {
				a.applyPowerUp();
				currentTile.setType('E'); // collected, now empty
				System.out.println("Agent " + a.getId() + " collected a POWER-UP!");
			} else if (currentTile.getType() == 'G') {
				System.out.println("Agent " + a.getId() + " has reached the GOAL!");
			}
		} else {
			System.out.println("Invalid move. Agent " + a.getId() + " stays at (" + oldX + ", " + oldY + ")");
		}
	}
	
	public void checkTileEffect(Agent a, MazeTile tile) {
		if (tile.getType() == 'T') {
			System.out.println("Agent " + a.getId() + " stepped on a trap and must backtrack!");
			a.backtrack();
			a.backtrack(); // trap forces two backtracks
		} else if (tile.getType() == 'P') {
			System.out.println("Agent " + a.getId() + " collected a power-up!");
			a.applyPowerUp();
		}
	}
	
	public void printFinalStatistics() {
		maze.printMazeSnapshot();

		System.out.println("\nTotal Turns Executed: " + turnCount);
	}

	public void logGameSummaryToFile(String filename) {
		try (FileWriter writer = new FileWriter(filename)) {
			writer.write("===== GAME SUMMARY =====\n");
			writer.write("Total Turns: " + turnCount + "\n\n");

			Queue<Agent> agentQueue = turns.getAgentQueue(); //Take the agentQueue from TurnManager class.

			// Iterate over the agentQueue to log details of each agent
			Queue<Agent> tempQueue = new Queue<>();

			// Copy the agents from agentQueue to tempQueue to avoid modifying the original queue
			while (!agentQueue.isEmpty()) {
				tempQueue.enqueue(agentQueue.dequeue());
			}

			while (!tempQueue.isEmpty()) {
				Agent agent = tempQueue.dequeue();
				writer.write("Agent ID: " + agent.getId() + "\n");
				writer.write("Final Position: (" + agent.getCurrentX() + ", " + agent.getCurrentY() + ")\n");
				writer.write("Total Moves: " + agent.getTotalMoves() + "\n");
				writer.write("Power Up Active: " + (agent.isHasPowerUp() ? "Yes" : "No") + "\n");
				writer.write("Total Backtracks: " + agent.getBacktracks() + "\n");
				writer.write("Last 5 Moves:\n");

				Stack<String> temp = new Stack<>();
				Stack<String> moveHistory = agent.getMoveHistory();

				int totalMoves = moveHistory.size();
				int movesToPrint = Math.min(5, totalMoves);

				for (int i = 0; i < movesToPrint; i++) {
					String move = moveHistory.pop();
					writer.write(move + "\n");
					temp.push(move);
				}

				while (!temp.isEmpty()) {
					moveHistory.push(temp.pop());
				}

				writer.write("\n");
			}

			writer.write("Simulation Ended.\n");
		} catch (IOException e) {
			System.out.println("An error occurred while writing the game summary to the file.");
			e.printStackTrace();
		}
	}
}
