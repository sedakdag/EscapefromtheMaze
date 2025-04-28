package com.escape.maze.simulation;

import com.escape.maze.structures.Stack;
import com.escape.maze.manager.MazeManager;
import com.escape.maze.manager.TurnManager;
import com.escape.maze.model.Agent;
import com.escape.maze.model.MazeTile;

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
			Agent agent = new Agent(i, maze.getStartX(), maze.getStartY(), new Stack<>(), false, 0, 0, false);
			turns.add(agent);
			maze.updateAgentLocation(agent, maze.getStartX(), maze.getStartY());
		}

		System.out.println("\nGame Initialized with " + numAgents + " agents.");
		maze.printMazeSnapshot();
	}
	
	public void runSimulation() {
		while(!turns.allAgentsFinished() && turnCount < maxTurns) {
			System.out.println("\n Turn " + (turnCount + 1));

			//TEK KORIDOR OLCAK SEKILDE DUZENLE
			maze.rotateNextCorridor();

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
		
	}
	
	public void checkTileEffect(Agent a, MazeTile tile) {
		
	}
	
	public void printFinalStatistics() {
		
	}
	
	public void logGameSummaryToFile(String filename) {
		
	}
	
}
