package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class test {

	public static void main(String[] args) {

		Date today = new Date();
		Block root = new Block(0, "ROOT_HASH", "ROOT", new BidData(0,0,0, today));

    	System.out.println("start");
    	String item_id = "1";
    	List<Block> bc = new ArrayList<>();
    	bc.add(new Block(1,"hash", "dd", new BidData(1,1,1000,today)));
    	bc.add(new Block(2,"hash", "dd", new BidData(2,1,2000,today)));
    	bc.add(new Block(3,"hash", "dd", new BidData(3,1,3000,today)));
    	bc.add(new Block(4,"hash", "dd", new BidData(2,2,1000,today)));
    	bc.add(new Block(5,"hash", "dd", new BidData(1,3,1000,today)));
    	
    	
        List<Block> temp = new ArrayList<>();
        System.out.println("start?");
        	
            for(Block b : bc){
            	if(b.getBidData().item_id == Integer.parseInt(item_id))
            	{
            		temp.add(b);
            		System.out.println(b.toString());
            	}
            }
            
            int winner_id = 0;
            
            Collections.sort(temp);
   
            
            winner_id = temp.get(temp.size()-1).getBidData().getUserID();	
       
            String result = "user_id="+winner_id+"&item_id"+item_id;
                
            System.out.println(result);
    	}
    	

	}


