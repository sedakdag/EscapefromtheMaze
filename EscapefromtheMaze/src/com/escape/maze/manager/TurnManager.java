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
        boolean reachedGoal = agent.isHasReachedGoal();//geçiçi olarak reachedgoal açtım, agenttaki hasreachedgoal e bağlı

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
    //ID,pozisyon,toplam move, power up kullanımı, backtrackler ve son 5 poizyon özeti
    public void logTurnSummary(Agent agent) {
        System.out.println("Turn:" + currentRound);

        System.out.println("ID of the Agent:" + agent.getId());
        System.out.println("Current Position:(" + agent.getCurrentX() + ", " + agent.getCurrentY() + ")");
        System.out.println("Total Moves: " + agent.getTotalMoves());
        System.out.println("Power Up Active: " + agent.isHasPowerUp());
       //linelara bir kere daha bak, eksik ya da hatalı kod olabilir.
        if (agent.isHasPowerUp()) {
            System.out.println("Agent " + agent.getId() + " used a Power Up");
        }
        System.out.println("Backtracks: " + agent.getBacktracks());
        if (agent.getBacktracks() > 0) {
            System.out.println("Agent " + agent.getId() + " backtracked " + agent.getBacktracks() + " times");
        }

        System.out.println("Positions of The Last 5 Moves:");
        Stack<String> temp = new Stack<>(); //temporoary stack oluşturdum
        int count = 0;

        while (!agent.getMoveHistory().isEmpty() && count < 5) { //move ghistory boş değilse ve 5 adım atmadıysa
            String move = agent.getMoveHistory().pop(); //son hareketi aldım
            System.out.println(move);
            temp.push(move); //temporary kaydettim ki string de silinmesin
            count++;
        }
// move historyden bilgileri aldığım için boşaldı, geri doldurmam gerek
        while (!temp.isEmpty()) {
            agent.getMoveHistory().push(temp.pop());
        }
    }
}
