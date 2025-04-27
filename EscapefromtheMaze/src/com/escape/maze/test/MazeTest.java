package com.escape.maze.test;

import com.escape.maze.manager.MazeManager;

public class MazeTest {
	 public static void main(String[] args) {

	        MazeManager mazeManager = new MazeManager(6, 6);

	        mazeManager.generateMaze();

	        System.out.println("Generated Maze:");
	        mazeManager.printMazeSnapshot();

	        System.out.println("\nAfter rotating one corridor:");
	        mazeManager.rotateNextCorridor();
	        mazeManager.printMazeSnapshot();

	        boolean solvable = mazeManager.isMazeSolvable(mazeManager.getStartX(), mazeManager.getStartY());
	        System.out.println("\nIs maze solvable from starting point? " + solvable);
	    }
}
