package agent;

import java.util.ArrayList;
import java.util.List;

import agent.Block.BidData;

import java.util.Date;

public class AgentManager {

    private List<Agent> agents = new ArrayList<>();
    private static Date today = new Date();
    private static Block temp = new Block();
    //private static Block.BidData root_data = temp.new BidData();
    private static final Block root = new Block(0, "ROOT_HASH", "ROOT", "BidData[user_id-0/item_id-0/bidding_price-0/auto_bid_price-0/bid_time-0]");

    public Agent addAgent(String name, int port) {
        Agent a = new Agent(name, "localhost", port, root, agents);
        a.startHost();
        agents.add(a);
        return a;
    }

    public Agent getAgent(String name) {
        for (Agent a : agents) {
            if (a.getName().equals(name)) {
                return a;
            }
        }
        return null;
    }

    public List<Agent> getAllAgents() {
        return agents;
    }

    public void deleteAgent(String name) {
        final Agent a = getAgent(name);
        if (a != null) {
            a.stopHost();
            agents.remove(a);
        }
    }

    public List<Block> getAgentBlockchain(String name) {
        final Agent agent = getAgent(name);
        if (agent != null) {
            return agent.getBlockchain();
        }
        return null;
    }

    public void deleteAllAgents() {
        for (Agent a : agents) {
            a.stopHost();
        }
        agents.clear();
    }

    public Block createBlock(final String name, String data) {
        final Agent agent = getAgent(name);
        //int port = agent.getPort();
        if (agent != null) {
            return agent.createBlock(data);
        }
        return null;
    }
}
