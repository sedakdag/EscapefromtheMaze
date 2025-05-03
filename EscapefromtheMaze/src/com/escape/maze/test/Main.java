package com.escape.maze.test;

import com.escape.maze.simulation.GameController;
import com.escape.maze.manager.MazeManager;
import com.escape.maze.manager.TurnManager;

public class Main {
    public static void main(String[] args) {
        int mazeWidth = 4;    // maze genişliği
        int mazeHeight = 4;           ;   // maze yüksekliği
        int numAgents = 2;    // agent sayısı
        int maxTurns = 200;   // maksimum turn sayısı

        System.out.println("Initializing the Maze Simulation...");

        // önce MazeManager ve TurnManager nesnelerini oluştur
        MazeManager mazeManager = new MazeManager(mazeWidth, mazeHeight);
        TurnManager turnManager = new TurnManager();

        // sonra GameController'a bunları ver
        GameController controller = new GameController(mazeManager, turnManager, maxTurns, numAgents);

        // simülasyonu başlat
        controller.runSimulation();

        System.out.println("Simulation finished. Summary written to 'game_summary.txt'.");
    }
}
