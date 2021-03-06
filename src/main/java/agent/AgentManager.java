package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;

public class AgentManager {

    private List<Agent> agents = new ArrayList<>();
    private static Date today = new Date();
    private static final Block root = new Block(0, "ROOT_HASH", "ROOT", new BidData(0,0,0, today));

    public Agent addAgent(String name, int port) {
    	for(Agent a : agents){
    		if(a.getName().equals(name)){
    			return a;
    		}
    	}
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
    
    public String getWinner(String itemid)
    {
    	System.out.println("Calculate winner!");
    	String result = "user_id=-1&item_id=-1";	
    	if(agents.size()!=0)
    	{
            final Agent agent = agents.get(0);
            List<Block> bc = agent.getBlockchain();
            List<Block> temp = new ArrayList<>();
        	
            for(Block b : bc){
            	if(b.getBidData().getItemID() == Integer.parseInt(itemid))
            	{
            		temp.add(b);
            	}
            }
            
            if(temp.size() != 0)
            {
            	int winner_id = 0;
                
                Collections.sort(temp);

                winner_id = temp.get(temp.size()-1).getBidData().getUserID();	
           
                result = "user_id="+winner_id+"&item_id="+itemid;
                System.out.println("Winneris" + result);
            }
    
    	}
    	return result;
    
    }
}
