package com.escape.maze.test;

import com.escape.maze.simulation.GameController;
import com.escape.maze.manager.MazeManager;
import com.escape.maze.manager.TurnManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Maze!");

        System.out.println("Please enter the maze's width: ");
        int mazeWidth = scanner.nextInt();

        System.out.println("Please enter the maze's height: ");
        int mazeHeight = scanner.nextInt();

        System.out.println("Please enter the number of agents: ");
        int numAgents = scanner.nextInt();

        System.out.println("Please enter the maximum number of turns: ");
        int maxTurns =  scanner.nextInt();

        System.out.println("Initializing the Maze Simulation...");

        MazeManager mazeManager = new MazeManager(mazeWidth, mazeHeight);
        TurnManager turnManager = new TurnManager();

        GameController controller = new GameController(mazeManager, turnManager, maxTurns, numAgents);
        
        controller.runSimulation();

        System.out.println("Simulation finished. Summary written to 'game_summary.txt'.");
    }
}
