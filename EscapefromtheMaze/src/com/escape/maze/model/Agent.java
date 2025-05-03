package com.escape.maze.model;

import java.util.Random;
import com.escape.maze.structures.AgentStack;
import com.escape.maze.manager.MazeManager;

public class Agent {
    private int id;
    private int currentX, currentY;
    private AgentStack<String> moveHistory;
    private boolean hasReachedGoal;
    private int totalMoves;
    private int backtracks;
    private boolean hasPowerUp;

    public Agent(int id, int currentX, int currentY, AgentStack<String> moveHistory, boolean hasReachedGoal, int totalMoves, int backtracks, boolean hasPowerUp) {
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
        int previousX = currentX;
        int previousY = currentY;

        if (!mazeManager.isValidMove(currentX, currentY, direction)) {
            return;
        }


        if (direction.toUpperCase().equals("UP")) {
            currentY--;
        } else if (direction.toUpperCase().equals("DOWN")) {
            currentY++;
        } else if (direction.toUpperCase().equals("LEFT")) {
            currentX--;
        } else if (direction.toUpperCase().equals("RIGHT")) {
            currentX++;
        }

        MazeTile currentTile = maze[currentY][currentX];


        if (currentTile.getType() == 'T') {
            mazeManager.updateAgentLocation(this, previousX, previousY);
            return;
        }


        if (currentTile.getType() == 'P' && !(currentX == mazeManager.getStartX() && currentY == mazeManager.getStartY())) {
            applyPowerUp();
        }


        mazeManager.updateAgentLocation(this, previousX, previousY);
        totalMoves++;
        recordMove(previousX, previousY);
    }

    public void backtrack(MazeManager mazeManager) {
        Random random = new Random();
        int pops = random.nextInt(2) + 1; // 1 or 2 pop

        int oldX = currentX;
        int oldY = currentY;

        while (!moveHistory.isEmpty() && pops > 0) {
            String lastMove = moveHistory.pop();
            String[] coords = lastMove.split(" ");
            currentX = Integer.parseInt(coords[0]);
            currentY = Integer.parseInt(coords[1]);
            backtracks++;
            pops--;
        }

        //Update the position (clear the old position, mark the new one)
        mazeManager.updateAgentLocation(this, oldX, oldY);
    }

    public void applyPowerUp() {
        hasPowerUp = true;}

    public void updatePositionWithRotation(int newX, int newY) {
        if (!moveHistory.isEmpty()) {
            moveHistory.pop(); //Remove the old position
        }
        setPosition(newX, newY);
        recordMove(newX, newY);
    }
    public void recordMove(int x, int y) {
        moveHistory.push(x + " " + y);
    }

    public String getMoveHistoryAsString() {
        return moveHistory.toString();
    }

    public void setHasReachedGoal(boolean hasReachedGoal) {
        this.hasReachedGoal = hasReachedGoal;
    }

    public void setPosition(int currentX, int currentY) {
        this.currentX = currentX;
        this.currentY = currentY;
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

    public AgentStack<String> getMoveHistory() {
        return moveHistory;
    }
}
