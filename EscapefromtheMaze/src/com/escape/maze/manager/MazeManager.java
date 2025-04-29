package com.escape.maze.manager;

import com.escape.maze.structures.SinglyLinkedList;
import com.escape.maze.structures.CircularLinkedList;
import com.escape.maze.structures.Queue;
import com.escape.maze.model.MazeTile;
import com.escape.maze.model.Agent;

import java.util.Random;

public class MazeManager {
	private MazeTile[][] grid;
	private int width, height;
	private int startX, startY;
	Random random = new Random();
	private SinglyLinkedList<Agent> agentList;
	private CircularLinkedList<Integer> rotatingRows;
	
	public MazeManager(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new MazeTile[height][width];
        this.agentList = new SinglyLinkedList<>();
        this.rotatingRows = new CircularLinkedList<>();
    }
	
	public void generateMaze() {
		boolean solvable = false;
		
		while(!solvable) {
			//Regenerate the maze
			for(int i = 0; i < width; i++) {
				for(int j = 0; j < height; j++) {
					char type;
					int randomValue = random.nextInt(100);
					if(randomValue < 10) {
						type = 'W'; //wall
					} else if(randomValue < 20) {
						type = 'T'; //trap
					} else if(randomValue < 25) {
						type = 'P'; //power-up
					} else {
						type = 'E'; //empty
					}
					grid[i][j] = new MazeTile(j,i,type);
				}
			}
			
			//Set the goal
			int goalX = random.nextInt(width);
			int goalY = random.nextInt(height);
			grid[goalX][goalY].setType('G');
			
			//Random start point
			startX = random.nextInt(width);
			startY = random.nextInt(height);
			
			//Check if it's solvable
			solvable = isMazeSolvable(startX, startY);
		}
	}
	
	//Check if the maze is solvable or not
	public boolean isMazeSolvable(int startX, int startY) {
		boolean[][] visited = new boolean[height][width];
		Queue<int[]> queue = new Queue<>();

		 queue.enqueue(new int[] {startX, startY});
		 visited[startY][startX] = true;

		 int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}}; //right, down, left, up

		 while (!queue.isEmpty()) {
		        int[] current = queue.dequeue();
		        int x = current[0];
		        int y = current[1];

		        if (grid[y][x].getType() == 'G') {
		            return true; //reached the goal
		        }

		        for (int[] dir : directions) {
		            int newX = x + dir[0];
		            int newY = y + dir[1];

		            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
		                if (!visited[newY][newX] && grid[newY][newX].isTraversable()) {
		                    visited[newY][newX] = true;
		                    queue.enqueue(new int[]{newX, newY});
		                }
		            }
		        }
		    }
		    return false; //couldn't find the goal

	}

	//Shifts a row to the right
	public void rotateCorridor(int rowId) {
		if (rowId < 0 || rowId >= height) return;
		
		MazeTile first = grid[rowId][0];
        for (int i = 0; i < width - 1; i++) {
            grid[rowId][i] = grid[rowId][i + 1];
        }
        grid[rowId][width - 1] = first;
	}
	
	//Shifts the next row in the rotatingRows list
	public void rotateNextCorridor() {
		//Randomly choose only one rotating row
		int randomRow = random.nextInt(height);
		rotatingRows.add(randomRow);

	    if (!rotatingRows.isEmpty()) {
	        int rowId = rotatingRows.getCurrent(); //current rowId
			System.out.println("Row " + (rowId + 1) + " is rotated.\n");
			rotateCorridor(rowId);
	        rotatingRows.moveNext(); //go to the next rowId
	    }
	}
	
	//Check if a move is valid or not
	public boolean isValidMove(int fromX, int fromY, String direction) {
		int newX = fromX;
        int newY = fromY;

        switch (direction.toUpperCase()) {
            case "UP":
                newY--;
                break;
            case "DOWN":
                newY++;
                break;
            case "LEFT":
                newX--;
                break;
            case "RIGHT":
                newX++;
                break;
            default:
                return false;
        }
        
        if (newX < 0 || newX >= width || newY < 0 || newY >= height) {
            return false;
        }
        MazeTile tile = grid[newY][newX];
        return tile.isTraversable();
	}
	
	//Give the tile in a given coordinate
	public MazeTile getTile(int x, int y) {
		if (x >= 0 && x < width && y >= 0 && y < height) {
            return grid[y][x];
        }
        return null;
	}
	
	//Update the agent's location
	public void updateAgentLocation(Agent a, int oldX, int oldY) {
		if (oldX >= 0 && oldX < width && oldY >= 0 && oldY < height) {
            grid[oldY][oldX].setHasAgent(false);
        }
        grid[a.getCurrentY()][a.getCurrentX()].setHasAgent(true);
	}

	// Print the maze to the console
	public void printMazeSnapshot() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print((i == startY && j == startX ? "S" : grid[i][j].toString()) + " ");
			}
			System.out.println();
		}
	}

	public int getStartX() {
	    return startX;
	}

	public int getStartY() {
	    return startY;
	}

	public MazeTile[][] getGrid() {
		return grid;
	}

	public String getSnapshotAsString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (i == startY && j == startX && grid[i][j].hasAgent()) {
					sb.append("S ");
				} else if (grid[i][j].hasAgent()) {
					sb.append("A ");
				} else {
					sb.append(grid[i][j].getType()).append(" ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
