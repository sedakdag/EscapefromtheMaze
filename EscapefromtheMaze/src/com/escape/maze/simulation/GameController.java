package com.escape.maze.simulation;

import com.escape.maze.structures.Stack;
import com.escape.maze.structures.SinglyLinkedList;
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
	private int totalTrapsTriggered = 0;
	private Random rand;
	private String initialMazeSnapshot;
	private SinglyLinkedList<Agent> allAgents = new SinglyLinkedList<>();
	private SinglyLinkedList<ReachedGoalInfo> agentsReachedGoal = new SinglyLinkedList<>();

	private static class ReachedGoalInfo {
		int id;
		int turn;

		public ReachedGoalInfo(int id, int turn) {
			this.id = id;
			this.turn = turn;
		}
	}

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
			allAgents.add(agent);
		}

		initialMazeSnapshot = maze.getSnapshotAsString();

		System.out.println("\nGame Initialized with " + numAgents + " agents.");
		maze.printMazeSnapshot();
	}

	public void runSimulation() {
		while (!turns.allAgentsFinished() && turnCount < maxTurns) {
			System.out.println("\n===== TURN " + (turnCount + 1) + " =====");

			maze.rotateNextCorridor();
			maze.printMazeSnapshot();
			System.out.println("\n");

			Agent currentAgent = turns.getCurrentAgent();
			if (currentAgent != null && !currentAgent.isHasReachedGoal()) {
				processAgentAction(currentAgent);
			}

			turns.advanceTurn();
			turns.logTurnSummary(currentAgent);

			turnCount++;
		}

		printFinalStatistics();

		logGameSummaryToFile("game_summary.txt");
	}

	public void processAgentAction(Agent a) {
		String[] directions = {"UP", "DOWN", "LEFT", "RIGHT"};
		String direction = directions[rand.nextInt(directions.length)];

		int oldX = a.getCurrentX();
		int oldY = a.getCurrentY();

		if (maze.isValidMove(oldX, oldY, direction)) {
			a.move(direction, maze.getGrid(), maze);

			MazeTile currentTile = maze.getTile(a.getCurrentX(), a.getCurrentY());
			if (currentTile == null) return;

			checkTileEffect(a, currentTile);

			if (currentTile.getType() == 'G' && !a.isHasReachedGoal()) {
				a.setHasReachedGoal(true);
				agentsReachedGoal.add(new ReachedGoalInfo(a.getId(), turnCount + 1)); // listeye ekle
				System.out.println("Agent " + a.getId() + " has reached the GOAL!");
			}
		} else {
			System.out.println("Invalid move. Agent " + a.getId() + " stays at (" + oldX + ", " + oldY + ")");
		}
	}

	private void checkTileEffect(Agent a, MazeTile tile) {
		if (tile.getType() == 'T') {
			System.out.println("Agent " + a.getId() + " stepped on a trap and must backtrack!");
			a.backtrack();
			totalTrapsTriggered++;
		} else if (tile.getType() == 'P') {
			System.out.println("Agent " + a.getId() + " collected a power-up!");
			a.applyPowerUp();
			tile.setType('E');
		}
	}

	public void printFinalStatistics() {
		maze.printMazeSnapshot();
		System.out.println("\nTotal Turns Executed: " + turnCount);
	}

	public void logGameSummaryToFile(String filename) {
		try (FileWriter writer = new FileWriter(filename)) {
			writer.write("===== SIMULATION SUMMARY =====\n\n");
			writer.write(initialMazeSnapshot + "\n\n");

			writer.write("Total Turns: " + turnCount + "\n");

			if (!agentsReachedGoal.isEmpty()) {
				ReachedGoalInfo winner = agentsReachedGoal.get(0);
				writer.write("Winner: Agent " + winner.id + " (First to reach the goal)\n");
			} else {
				writer.write("Winner: No agent reached the goal.\n");
			}

			writer.write("Agents Reached Goal:\n");
			for (int i = 0; i < agentsReachedGoal.size(); i++) {
				ReachedGoalInfo info = agentsReachedGoal.get(i);
				writer.write("- Agent " + info.id + " (Turn: " + info.turn + ")\n");
			}

			writer.write("\n");
			for (int i = 0; i < allAgents.size(); i++) {
				Agent agent = allAgents.get(i);
				writer.write("Agent " + agent.getId() + " Stats:\n");
				writer.write("Total Steps: " + agent.getTotalMoves() + "\n");
				writer.write("Backtracks: " + agent.getBacktracks() + "\n\n");
			}

			writer.write("Total Traps Triggered: " + totalTrapsTriggered + "\n\n");
			writer.write("Last Snapshot of the Maze:\n\n");
			writer.write(maze.getSnapshotAsString());

		} catch (IOException e) {
			System.out.println("An error occurred while writing the game summary to the file.");
			e.printStackTrace();
		}
	}
}
