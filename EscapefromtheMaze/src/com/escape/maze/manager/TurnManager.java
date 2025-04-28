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
    public void advanceTurn(){
        if(agentQueue.isEmpty()){
            return;
        }
        Agent agent = agentQueue.dequeue();
        boolean reachedGoal = agent.isHasReachedGoal();//i added a temporary reachedgoal, it impoted hasreachedgoal from agent

        if (!reachedGoal){
            agentQueue.enqueue(agent);
        }
        currentRound++;

    }
    public Agent getCurrentAgent() {
        if(agentQueue.isEmpty()){
            return null;
        }
        return agentQueue.peek();
    }
    public boolean allAgentsFinished(){
        return agentQueue.isEmpty();
    }
    // summary of ID,position ,total moves, if it as used power up , backtracks and  last 5 moves
    public void logTurnSummary(Agent agent) {
        System.out.println("Turn:" + currentRound);

        System.out.println("ID of the Agent:" + agent.getId());
        System.out.println("Current Position:(" + agent.getCurrentX() + ", " + agent.getCurrentY() + ")");
        System.out.println("Total Moves: " + agent.getTotalMoves());
        System.out.println("Power Up Active: " + agent.isHasPowerUp());
       //look at the LINES AGAIN THERE MIGHT BE SOMETHING YOU HAVE FORGOTTEN
        if (agent.isHasPowerUp()) {
            System.out.println("Agent " + agent.getId() + " used a Power Up");
        }
        System.out.println("Backtracks: " + agent.getBacktracks());
        if (agent.getBacktracks() > 0) {
            System.out.println("Agent " + agent.getId() + " backtracked " + agent.getBacktracks() + " times");
        }

//i got some help while writing last 5 moves
        System.out.println("Positions of The Last 5 Moves:");
        Stack<String> temp = new Stack<>(); //temporoary stack oluşturdum
        int count = 0;

        while (!agent.getMoveHistory().isEmpty() && count < 5) { //if move history is not empty and hast moved 5 times
            String move = agent.getMoveHistory().pop(); //popped last move
            System.out.println(move);
            temp.push(move); //i did it temporary so it doesnt remove it from the stirng
            count++;
        }
// move history is empty since i got the info from it, i have to fill it up again
        while (!temp.isEmpty()) {
            agent.getMoveHistory().push(temp.pop());
        }
    }
}
