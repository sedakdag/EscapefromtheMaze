package com.escape.maze.manager;
import com.escape.maze.structures.Queue;
import com.escape.maze.model.Agent;
import com.escape.maze.structures.Stack;

public class TurnManager {
    private Queue<Agent> agentQueue;
    private int currentRound;

    public TurnManager() {
        agentQueue = new Queue<>();
        currentRound = 0;
    }

    public void add(Agent agent) {
        agentQueue.enqueue(agent);
    }

    public void advanceTurn() {
        if (agentQueue.isEmpty()) {
            return;
        }
        Agent agent = agentQueue.dequeue();
        boolean reachedGoal = agent.isHasReachedGoal();//i added a temporary reachedgoal, it impoted hasreachedgoal from agent

        if (!reachedGoal) {
            agentQueue.enqueue(agent);
        }
        currentRound++;

    }

    public Agent getCurrentAgent() {
        if (agentQueue.isEmpty()) {
            return null;
        }
        return agentQueue.peek();
    }

    public boolean allAgentsFinished() {
        return agentQueue.isEmpty();
    }

    public Queue<Agent> getAgentQueue() {
        return agentQueue;
    }

    // summary of ID,position ,total moves, if it as used power up , backtracks and  last 5 moves
    public void logTurnSummary(Agent agent) {
        System.out.println("\n========== Turn:" + currentRound + " ==========\n");
        System.out.println(">> AGENT INFORMATION <<");
        System.out.println("ID:" + agent.getId());
        System.out.println("Current Position:(" + agent.getCurrentX() + ", " + agent.getCurrentY() + ")");
        System.out.println("Total Moves: " + agent.getTotalMoves());
        System.out.println("Power Up Active: " + (agent.isHasPowerUp() ? "Yes" : "No"));
        //look at the LINES AGAIN THERE MIGHT BE SOMETHING YOU HAVE FORGOTTEN
        if (agent.isHasPowerUp()) {
            System.out.println("Action:Agent " + agent.getId() + " used a Power-Up!");
        }
        System.out.println("Backtracks: " + agent.getBacktracks());
        if (agent.getBacktracks() > 0) {
            System.out.println("Action:Agent " + agent.getId() + " backtracked " + agent.getBacktracks() + " times");
        }

//i got some help while writing last 5 moves
        System.out.println("\n>> LAST 5 MOVES <<");
        System.out.printf("| %-8s | %-14s |\n", "Move No", "Position (x,y)");
        System.out.println("|----------|----------------|");

        Stack<String> originalHistory = agent.getMoveHistory();


        // a temp copy stack for moveHistory
        Stack<String> copy = new Stack<>();
        Stack<String> temp = new Stack<>();

        while (!originalHistory.isEmpty()) {
            String move = originalHistory.pop();
            copy.push(move);
            temp.push(move);
        }

        // turning moveHistory back to normal
        while (!temp.isEmpty()) {
            originalHistory.push(temp.pop());
        }

        // taking 5 moves from copy
        int count = 1;
        while (!copy.isEmpty() && count <= 5) {
            String move = copy.pop();
            System.out.printf("| %-8d | %-14s |\n", count, move);
            count++;

        }
        System.out.println("\n=============================================\n");
    }
}