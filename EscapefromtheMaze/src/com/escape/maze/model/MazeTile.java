package com.escape.maze.model;

public class MazeTile {
    private int x, y; // coordinates
    private char type; // 'E', 'W', 'T', 'P', 'G'
    private boolean hasAgent;

    public MazeTile(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hasAgent = false;
    }

    public boolean isTraversable() {
        return type != 'W'; // wall değilse geçebilir
    }

    public String toString() {
        if (hasAgent) return "A";
        return String.valueOf(type);
    }

    // Getter ve Setter'lar
    public int getX() { return x; }
    public int getY() { return y; }
    public char getType() { return type; }
    public boolean hasAgent() { return hasAgent; }

    public void setType(char type) { this.type = type; }
    public void setHasAgent(boolean hasAgent) { this.hasAgent = hasAgent; }
}


