package com.escape.maze.model;

import java.util.Random;
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
        int previousX = currentX;
        int previousY = currentY;

        if (!mazeManager.isValidMove(currentX, currentY, direction)) {
            return;
        }

        // Hareketi gerçekleştir
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

        // Eğer yeni konum T ise backtrack yap ve işlemi sonlandır
        if (currentTile.getType() == 'T') {
            // Önce eski konuma geri dön
            //System.out.println("Agent " + "ajan" + " stepped on a trap and must backtrack!");
            //currentX = previousX;
            //currentY = previousY;
            //backtrack(mazeManager);
            return;
        }

        // Eğer P (PowerUp) ise ve başlangıç noktası değilse power-up uygula
        if (currentTile.getType() == 'P' && !(currentX == mazeManager.getStartX() && currentY == mazeManager.getStartY())) {
            applyPowerUp();
        }

        // Konumu güncelle
        mazeManager.updateAgentLocation(this, previousX, previousY);
        totalMoves++;
        recordMove(previousX, previousY); // Eski konumu kaydet
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

        // Konumu güncelle (eski konumu temizle, yeni konumu işaretle)
        mazeManager.updateAgentLocation(this, oldX, oldY);
    }

    public void applyPowerUp() {
        hasPowerUp = true;}

    public void updatePositionWithRotation(int newX, int newY) {
        if (!moveHistory.isEmpty()) {
            moveHistory.pop(); // Eski pozisyonu sil
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

    public Stack<String> getMoveHistory() {
        return moveHistory;
    }
}
