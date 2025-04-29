package com.escape.maze.test;

import com.escape.maze.simulation.GameController;
import com.escape.maze.manager.MazeManager;
import com.escape.maze.manager.TurnManager;

public class Main {
	public static void main(String[] args) {
		int mazeWidth = 6;    // maze genişliği
		int mazeHeight = 6;   // maze yüksekliği
		int numAgents = 3;    // agent sayısı
		int maxTurns = 100;   // maksimum turn sayısı

		System.out.println("Initializing the Maze Simulation...");

		// önce MazeManager ve TurnManager nesnelerini oluştur
		MazeManager mazeManager = new MazeManager(mazeWidth, mazeHeight);
		TurnManager turnManager = new TurnManager();

		// sonra GameController'a bunları ver
		GameController controller = new GameController(mazeManager, turnManager, maxTurns, numAgents);

		// simülasyonu başlat
		controller.runSimulation();

		// sonuçları dosyaya yaz
		controller.logGameSummaryToFile("game_summary.txt");

		System.out.println("Simulation finished. Summary written to 'game_summary.txt'.");
	}
}
