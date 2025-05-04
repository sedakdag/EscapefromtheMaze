package com.escape.maze.manager;
import com.escape.maze.structures.TurnQueue;
import com.escape.maze.model.Agent;
import com.escape.maze.structures.AgentStack;

public class TurnManager {
    private TurnQueue<Agent> agentTurnQueue;
    private int currentRound;
    private TurnQueue<int[]> previousPositions;

    public TurnManager() {
        this.agentTurnQueue = new TurnQueue<>();
        this.previousPositions = new TurnQueue<>();
        this.currentRound = 0;

    }

    public void add(Agent agent) {
        agentTurnQueue.enqueue(agent);
        previousPositions.enqueue(new int[]{agent.getCurrentX(), agent.getCurrentY()});
    }

    public void advanceTurn() {
        if (agentTurnQueue.isEmpty()) {
            return;
        }
        Agent agent = agentTurnQueue.dequeue();
        int[] prevPos = previousPositions.dequeue();
        boolean reachedGoal = agent.isHasReachedGoal();

        if (prevPos[0] != agent.getCurrentX() || prevPos[1] != agent.getCurrentY()) {
            System.out.printf("Agent %d moved from %d,%d to %d,%d%n",
                    agent.getId(), prevPos[0], prevPos[1],
                    agent.getCurrentX(), agent.getCurrentY());
        }

        if (!reachedGoal) {
            agentTurnQueue.enqueue(agent);
            previousPositions.enqueue(new int[]{agent.getCurrentX(), agent.getCurrentY()});
        }
        currentRound++;

    }

    public Agent getCurrentAgent() {
        if (agentTurnQueue.isEmpty()) {
            return null;
        }
        return agentTurnQueue.peek();
    }

    public boolean allAgentsFinished() {
        return agentTurnQueue.isEmpty();
    }

    public TurnQueue<Agent> getAgentQueue() {
        return agentTurnQueue;
    }

    //Print summary of ID, position, total moves, if it as used power up, total backtracks and last 5 moves
    public void logTurnSummary(Agent agent) {
        System.out.println("========== Turn:" + currentRound + " ==========\n");
        System.out.println(">> AGENT INFORMATION <<");
        System.out.println("ID:" + agent.getId());
        System.out.println("Current Position:(" + agent.getCurrentX() + ", " + agent.getCurrentY() + ")");
        System.out.println("Total Moves: " + agent.getTotalMoves());
        System.out.println("Power Up Active: " + (agent.isHasPowerUp() ? "Yes" : "No"));
        //look at the LINES AGAIN THERE MIGHT BE SOMETHING YOU HAVE FORGOTTEN
        if (agent.isHasPowerUp()) {
            System.out.println("Agent " + agent.getId() + " has a Power-Up!");
        }
        System.out.println("Total Backtracks: " + agent.getBacktracks());
        if (agent.getBacktracks() > 0) {
            System.out.println("Agent " + agent.getId() + " backtracked " + agent.getBacktracks() + " times in total.");
        }

        //Print last 5 moves
        System.out.println("\n>> LAST 5 MOVES <<");
        System.out.printf("| %-8s | %-14s |\n", "Move No", "Position (x,y)");
        System.out.println("|----------|----------------|");

        AgentStack<String> originalHistory = agent.getMoveHistory();


        //A temporary copy stack for moveHistory
        AgentStack<String> copy = new AgentStack<>();
        AgentStack<String> temp = new AgentStack<>();

        while (!originalHistory.isEmpty()) {
            String move = originalHistory.pop();
            copy.push(move);
            temp.push(move);
        }

        //Turn moveHistory back to normal
        while (!temp.isEmpty()) {
            originalHistory.push(temp.pop());
        }

        //Take 5 moves from copy
        int count = 1;
        while (!copy.isEmpty() && count <= 5) {
            String move = copy.pop();
            String[] parts = move.split(" ");
            String formatted = "(" + parts[0] + "," + parts[1] + ")";
            System.out.printf("| %-8d | %-14s |\n", count, formatted);
            count++;

        }
        if (agent.isHasReachedGoal()) {
            System.out.println("\nAgent " + agent.getId() + " has reached the GOAL!");
        }

        System.out.println("\n=============================================\n");
    }

}