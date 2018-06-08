package agent;

import java.io.Serializable;
import java.util.Date;

public class BidData implements Comparable<BidData>,Serializable{
    private static final long serialVersionUID = 1L;

        int user_id;
        int item_id; 
        int bidding_price; 
        Date bid_time; 

        //toString
        @Override
        public String toString() {
          return "BidData{" +
                  "user_id=" + user_id +
                  ", item_id=" + item_id +
                  ", bidding_price=" + bidding_price +
                  ", bid_time='" + bid_time +
                  "'}";
         
       }

       public BidData()
       {
         this.user_id = 0;
         this.item_id = 0;
         this.bidding_price = 0;
         Date today = new Date();
         this.bid_time = today;
     
       }

       public BidData(int _user_id, int _item_id, int _bidding_price, Date _bid_time)
       {
         this.user_id = _user_id;
         this.item_id = _item_id;
         this.bidding_price = _bidding_price;
         this.bid_time = _bid_time;

       }

        public void AddBidData(int _user_id, int _item_id, int _bidding_price, Date _bid_time)
      {
        this.user_id = _user_id;
        this.item_id = _item_id;
        this.bidding_price = _bidding_price;
        this.bid_time = _bid_time;
      }

       public int getUserID() {
           return user_id;
       }

       public int getItemID() {
           return item_id;
       }

       public int getBiddingPrice() {
           return bidding_price;
       }


       public Date getBidTime() {
           return bid_time;
       }
       
       @Override
       public boolean equals(final Object o) {
           if (this == o) {
               return true;
           }
           if (o == null || getClass() != o.getClass()) {
               return false;
           }
           final BidData biddata = (BidData) o;
           return user_id == biddata.user_id
        		   && item_id == biddata.item_id
        		   && bidding_price == biddata.bidding_price	                   
                   && bid_time.equals(biddata.bid_time);
       }

       @Override
       public int hashCode() {
           int result = user_id;
           result = 31 * result + item_id;
           result = 31 * result + bidding_price;
           result = 31 * result + bid_time.hashCode();
           return result;
       }
       
       @Override
       public int compareTo(BidData bd)
       {
    	   if(this.bidding_price < bd.bidding_price){
    		   return -1;
    	   }else if(this.bidding_price == bd.bidding_price)
    	   {
    		   return 0;
    	   }
    	   else{
    		   return 1;
    	   }
       }
}
