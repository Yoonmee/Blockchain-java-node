package agent;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Date;

public class Block implements Serializable {
    private static final long serialVersionUID = 1L;

    public static String sayHello(){
      return "Hello!!";
    }

    public class BidData implements Serializable{
        int user_id; //寃쎈ℓ 李몄뿬 �쑀�� �븘�씠�뵒
        int item_id; //臾쇨굔 id
        int bidding_price; //�쑀�� 鍮꾨뵫 媛�寃�
        int auto_bid_price; //留뚯빟 auto bid �븷嫄곕㈃ 媛�寃�, �븘�땲硫� -1
        Date bid_time; //鍮꾨뵫 �떆媛�
        //int highest_price; //�벑濡� �떦�떆 �젣�씪 理쒓퀬媛�?


        //toString biddata �젙蹂� 異쒕젰
        public String BidDatatoString() {
          return "BidData[" +
                  "user_id-" + user_id +
                  "/item_id-" + item_id +
                  "/bidding_price-" + bidding_price +
                  "/auto_bid_price-" + auto_bid_price +
                  "/bid_time-" + bid_time +
                  "]";
                  //", highest_price='" + highest_price +
       }

       //�깮�꽦�옄
       public BidData()
       {
         this.user_id = 0;
         this.item_id = 0;
         this.bidding_price = 0;
         this.auto_bid_price = 0;
         Date today = new Date();
         //System.out.println(today);
         this.bid_time = today;
         //this.highest_price = _highest_price;
       }

       public BidData(int _user_id, int _item_id, int _bidding_price, int _auto_bid_price, Date _bid_time)
       {
         this.user_id = _user_id;
         this.item_id = _item_id;
         this.bidding_price = _bidding_price;
         this.auto_bid_price = _auto_bid_price;
         this.bid_time = _bid_time;
         //this.highest_price = _highest_price;
       }

        public void AddBidData(int _user_id, int _item_id, int _bidding_price, int _auto_bid_price, Date _bid_time)
      {
        this.user_id = _user_id;
        this.item_id = _item_id;
        this.bidding_price = _bidding_price;
        this.auto_bid_price = _auto_bid_price;
        this.bid_time = _bid_time;
        //this.highest_price = _highest_price;
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

       public int getAutoBidPrice() {
           return auto_bid_price;
       }

       public Date getBidTime() {
           return bid_time;
       }

//       public int getHighestPrice() {
//           return highest_price;
//       }
      }

    private int index;
    private Long timestamp;
    private String hash;
    private String previousHash;
    private String creator;
    private String data;

    // for jackson
    public Block() {
    }

    @Override
    public String toString() {
        return "Block{" +
                "index=" + index +
                ", timestamp=" + timestamp +
                ", creator=" + creator +
//               ", hash='" + hash + //"'\'" +
//               ", previousHash='" + previousHash +  //"'\'"  +
               ", data=" + data +
                "}";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Block block = (Block) o;
        return index == block.index
                && timestamp.equals(block.timestamp)
                && hash.equals(block.hash)
                && previousHash.equals(block.previousHash)
                && creator.equals(block.creator)
                && data.equals(block.data);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + hash.hashCode();
        result = 31 * result + previousHash.hashCode();
        result = 31 * result + creator.hashCode();
        return result;
    }

//    public Block(int index, String preHash, String creator, BidData data) {
//        this.index = index;
//        this.previousHash = preHash;
//        this.creator = creator;
//        this.data = data;
//        timestamp = System.currentTimeMillis();
//        hash = calculateHash(String.valueOf(index) + previousHash + String.valueOf(timestamp));
//    }

    public Block(String data) {
    	this.data = data;
    }
    
    public Block(int index, String preHash, String creator, String data) {
        this.index = index;
        this.previousHash = preHash;
        this.creator = creator;
        timestamp = System.currentTimeMillis();
        this.data = data;
        hash = calculateHash(String.valueOf(index) + previousHash + String.valueOf(timestamp));
    }

    public String getCreator() {
        return creator;
    }

    public int getIndex() {
        return index;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getBidData()
    {
        return data;
    }

    private String calculateHash(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "HASH_ERROR";
        }

        final byte bytes[] = digest.digest(text.getBytes());
        final StringBuilder hexString = new StringBuilder();
        for (final byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
