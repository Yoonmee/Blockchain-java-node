package agent;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block implements Comparable<Block>,Serializable {
    private static final long serialVersionUID = 1L;

   
    private int index;
    private Long timestamp;
    private String hash;
    private String previousHash;
    private String creator;
    private BidData data;
    //private String data;

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
               ", data=" + data.toString() +
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
        result = 31 * result + data.hashCode();
        return result;
    }

    public Block(int index, String preHash, String creator, BidData data) {
        this.index = index;
        this.previousHash = preHash;
        this.creator = creator;
        this.data = data;
        timestamp = System.currentTimeMillis();
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

    public BidData getBidData()
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
    
    @Override
    public int compareTo(Block b)
    {
 	   if(this.data.compareTo(b.data) == -1){
 		   return -1;
 	   }else if(this.data == b.data)
 	   {
 		   return 0;
 	   }
 	   else{
 		   return 1;
 	   }
    }
}
