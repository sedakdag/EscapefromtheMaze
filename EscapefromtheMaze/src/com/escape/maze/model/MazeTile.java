package com.escape.maze.model;

public class MazeTile {
    private int x;
    private int y;
    private char type; //'E' for empty,'W' for wall,'T' for trap,'P' for power up,'G' for goal
    private boolean hasAgent;// if true there is an agent

    public MazeTile(int x, int y, char type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.hasAgent = false; //there is no agent in the beginning
    }

    public boolean isTraversable() {

        return type != 'W'; // if there is no wall it can pass through
    }

    @Override
    public String toString() {
        return hasAgent ? "A": String.valueOf(type);
    }

    //get methods(read)
    public int getX() { return x; }
    public int getY() { return y; }
    public char getType() { return type; }
    public boolean hasAgent() { return hasAgent; }

    //set methods(change)
    public void setType(char type) { this.type = type; }
    public void setHasAgent(boolean hasAgent) { this.hasAgent = hasAgent; }
}


