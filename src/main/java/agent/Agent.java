package agent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.Date;

import static agent.Message.MESSAGE_TYPE.INFO_NEW_BLOCK;
import static agent.Message.MESSAGE_TYPE.READY;
import static agent.Message.MESSAGE_TYPE.REQ_ALL_BLOCKS;
import static agent.Message.MESSAGE_TYPE.RSP_ALL_BLOCKS;

public class Agent {

    private String name;
    private String address;
    private int port;
    private List<Agent> peers;
    private List<Block> blockchain = new ArrayList<>();

    private ServerSocket serverSocket;
    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);

    private boolean listening = true;

    // for jackson
    public Agent() {
    }

    Agent(final String name, final String address, final int port, final Block root, final List<Agent> agents) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.peers = agents;
        blockchain.add(root);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public List<Block> getBlockchain() {
        return blockchain;
    }

    Block createBlock(String data) {
        if (blockchain.isEmpty()) {
            return null;
        }

        Block previousBlock = getLatestBlock();
        if (previousBlock == null) {
            return null;
        }

        Date today = new Date();
        System.out.println(today);
        
        final int index = previousBlock.getIndex() + 1;
        
        int item_id = this.port / 1000;
        int user_id = this.port % 1000;
        int bid_price = Integer.parseInt(data);
        
        Block temp = new Block();
        Block.BidData _data = temp.new BidData();
        _data.AddBidData(user_id,item_id,bid_price,0,today);
        String BidData = _data.BidDatatoString();
        //final Block block = new Block(index, previousBlock.getHash(), name);
        final Block block = new Block(index, previousBlock.getHash(), name, BidData);
        System.out.println(String.format("%s created new block %s", name, block.toString()));
        System.out.println(String.format("bid data is string? %s", block.getBidData()));
        broadcast(INFO_NEW_BLOCK, block);
        return block;
    }

    void addBlock(Block block) {
        if (isBlockValid(block)) {
            blockchain.add(block);
        }
    }

    void startHost() {
        executor.execute(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println(String.format("Server %s started", serverSocket.getLocalPort()));
                listening = true;
                while (listening) {
                    final AgentServerThread thread = new AgentServerThread(Agent.this, serverSocket.accept());
                    thread.start();
                }
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Could not listen to port " + port);
            }
        });
        broadcast(REQ_ALL_BLOCKS, null);
    }

    void stopHost() {
        listening = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Block getLatestBlock() {
        if (blockchain.isEmpty()) {
            return null;
        }
        return blockchain.get(blockchain.size() - 1);
    }

    private boolean isBlockValid(final Block block) {
        final Block latestBlock = getLatestBlock();
        if (latestBlock == null) {
            return false;
        }
        final int expected = latestBlock.getIndex() + 1;
        if (block.getIndex() != expected) {
            System.out.println(String.format("Invalid index. Expected: %s Actual: %s", expected, block.getIndex()));
            return false;
        }
        if (!Objects.equals(block.getPreviousHash(), latestBlock.getHash())) {
            System.out.println("Unmatched hash code");
            return false;
        }
        return true;
    }

    private void broadcast(Message.MESSAGE_TYPE type, final Block block) {
        peers.forEach(peer -> sendMessage(type, peer.getAddress(), peer.getPort(), block));
    }

    private void sendMessage(Message.MESSAGE_TYPE type, String host, int port, Block... blocks) {
        try (
                final Socket peer = new Socket(host, port);
                final ObjectOutputStream out = new ObjectOutputStream(peer.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(peer.getInputStream())) {
            Object fromPeer;
            while ((fromPeer = in.readObject()) != null) {
                if (fromPeer instanceof Message) {
                    final Message msg = (Message) fromPeer;
                    System.out.println(String.format("%d received: %s", this.port, msg.toString()));
                    if (READY == msg.type) {
                        out.writeObject(new Message.MessageBuilder()
                                .withType(type)
                                .withReceiver(port)
                                .withSender(this.port)
                                .withBlocks(Arrays.asList(blocks)).build());
                    } else if (RSP_ALL_BLOCKS == msg.type) {
                        if (!msg.blocks.isEmpty() && this.blockchain.size() == 1) {
                            blockchain = new ArrayList<>(msg.blocks);
                        }
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            System.err.println(String.format("Unknown host %s %d", host, port));
        } catch (IOException e) {
            System.err.println(String.format("%s couldn't get I/O for the connection to %s. Retrying...%n", getPort(), port));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
