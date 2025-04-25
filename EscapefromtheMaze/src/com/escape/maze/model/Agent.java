package com.escape.maze.model;

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


    public void move(String direction) {

        if (direction.equals("UP")) {
            currentY++;
        } else if (direction.equals("DOWN")) {
            currentY--;
        } else if (direction.equals("LEFT")) {
            currentX--;
        } else if (direction.equals("RIGHT")) {
            currentX++;
        }

        totalMoves++;
        recordMove(currentX, currentY);

        if (type == "G"){
            hasReachedGoal = true;
        }
        if (type == "P"){
            applyPowerUp();
        }
        // mazemanager isValidMove kontrol edilecek
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
}
