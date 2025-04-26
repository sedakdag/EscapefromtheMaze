package com.escape.maze.simulation;

import com.escape.maze.manager.MazeManager;
import com.escape.maze.manager.TurnManager;
import com.escape.maze.model.Agent;
import com.escape.maze.model.MazeTile;

public class GameController {
	private MazeManager maze;
	private TurnManager turns;
	private int maxTurns;
	private int turnCount;
	
	public void initializeGame(int numAgents) {
		maze.generateMaze();
		runSimulation();
	}
	
	public void runSimulation() {
		
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
