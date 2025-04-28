package com.escape.maze.model;

import com.escape.maze.structures.Stack;
import com.escape.maze.manager.MazeManager;
import com.escape.maze.model.MazeTile;

public class Agent {
    private int id;
    private int currentX, currentY;
    private Stack<String> moveHistory;
    private boolean hasReachedGoal;
    private int totalMoves;
    private int backtracks;
    private boolean hasPowerUp;

    public Agent(int id, int currentX, int currentY, Stack<String> moveHistory, boolean hasReachedGoal, int totalMoves, int backtracks, boolean hasPowerUp) {
        this.id = id;
        this.currentX = currentX;
        this.currentY = currentY;
        this.moveHistory = moveHistory;
        this.hasReachedGoal = false;
        this.totalMoves = 0;
        this.backtracks = 0;
        this.hasPowerUp = false;
    }


    public void move(String direction, MazeTile[][] maze, MazeManager mazeManager) {
        if (!mazeManager.isValidMove(currentX, currentY, direction)) {
            return;
        }

        else if (direction.toUpperCase().equals("UP")) {
            currentY--;
        } else if (direction.toUpperCase().equals("DOWN")) {
            currentY++;
        } else if (direction.toUpperCase().equals("LEFT")) {
            currentX--;
        } else if (direction.toUpperCase().equals("RIGHT")) {
            currentX++;
        }

        totalMoves++;
        recordMove(currentX, currentY);

        MazeTile currentTile = maze[currentY][currentX];

        if (currentTile.getType() == 'G'){
            hasReachedGoal = true;
        }
        if (currentTile.getType() == 'P'){
            applyPowerUp();
        }

        mazeManager.updateAgentLocation(this, currentX, currentY);

    }

    public void backtrack() {
        if(moveHistory.isEmpty() == false) {
            moveHistory.pop();
            backtracks++;
        }
    }

    public void applyPowerUp() {
        hasPowerUp = true;}

    public void recordMove(int x, int y) {
        moveHistory.push(currentX + " " + currentY);
    }

    public String getMoveHistoryAsString() {
        return moveHistory.toString();
    }

    public int getCurrentX() {
        return currentX;
    }
    public int getCurrentY() {
        return currentY;
    }
    public int getTotalMoves() {
        return totalMoves;
    }
    public int getBacktracks() {
        return backtracks;
    }
    public int getId() {
        return id;
    }
    public boolean isHasPowerUp(){
        return hasPowerUp;
    }
    public boolean isHasReachedGoal() {
        return hasReachedGoal;
    }

    public Stack<String> getMoveHistory() {
        return moveHistory;
    }
}
