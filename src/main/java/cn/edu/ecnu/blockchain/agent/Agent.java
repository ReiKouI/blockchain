package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.exception.GlobalException;
import cn.edu.ecnu.blockchain.result.CodeMessage;
import cn.edu.ecnu.blockchain.util.AccountUtil;
import cn.edu.ecnu.blockchain.util.RSAUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static cn.edu.ecnu.blockchain.agent.Message.MESSAGE_TYPE.*;


@Log4j2
public class Agent {

    @Getter
    private String name;

    @Getter
    private String address;

    @Getter
    private int port;

    @Getter
    private List<Block> blockchain;

    @Getter
    private List<Transaction> transactions;

    @Getter
    private String publicKey;

    private String privateKey;
    private List<Agent> peers;
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
        this.blockchain = new ArrayList<>();
        blockchain.add(root);

        transactions = new ArrayList<>();

        KeyPair keyPair = RSAUtil.createKeyPair();
        publicKey = RSAUtil.encodeKey(keyPair.getPublic());
        privateKey = RSAUtil.encodeKey(keyPair.getPrivate());
    }

    Block createBlock() {
        return createBlock(null);
    }

    public double getAvailableAccount() {
        return AccountUtil.getAvailableAccount(blockchain, publicKey);
    }

    Transaction createTransactionTo(Agent receiver, double value) {
        if (value > AccountUtil.getAvailableAccount(blockchain, publicKey)) {
            throw new GlobalException(CodeMessage.INSUFFICIENT_BALANCE);
        }
        Transaction transaction = new Transaction(publicKey, name, receiver.publicKey, receiver.name, value);
        transaction.sign(privateKey);
        broadcastTransaction(INFO_NEW_TRANSACTION, transaction);
        return transaction;
    }

    public void broadcastTransaction(Message.MESSAGE_TYPE type, Transaction transaction) {
        peers.forEach(peer -> sendMessage(type, peer.getAddress(), peer.getPort(), transaction));
    }



    Block createBlock(Transaction transaction) {
        if (blockchain.isEmpty()) {
            log.warn("block chain is empty.");
            return null;
        }

        if (transaction != null && !transaction.isSignVerified()) {
            log.warn("transaction is not valid");
            return null;
        }

        Block previousBlock = getLatestBlock();
        if (previousBlock == null) {
            log.warn("previousBlock is null");
            return null;
        }

        final int index = previousBlock.getIndex() + 1;
        final Block block = new Block(index, previousBlock.getHash(), name, publicKey, transaction);
        block.sign(privateKey);
        System.out.println(String.format("%s created new block %s", name, block.toString()));
        broadcast(INFO_NEW_BLOCK, block);
        return block;
    }

    void addBlockFromOtherAgent(Block block) {
        if (isBlockValid(block)) {
            blockchain.add(block);
        }
    }

    void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    void invalidTransaction(Transaction transaction) {
        for (Transaction t : transactions) {
            if (t.getSignature().equals(transaction.getSignature()))
                t.setValid(false);
        }
    }

    Transaction getTransactionBySig(String signature) {
        for (Transaction transaction: transactions) {
            if (transaction.isValid() && transaction.getSignature().equals(signature))
                return transaction;
        }
        return null;
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
            log.warn(String.format("Invalid index. Expected: %s Actual: %s", expected, block.getIndex()));
            return false;
        }

        if (!Objects.equals(block.getPreviousHash(), latestBlock.getHash())) {
            log.warn("Unmatched hash code");
            return false;
        }

        if (block.getTransaction() != null && !block.getTransaction().isSignVerified()) {
            log.warn("The sign of transaction is not verified.");
            return false;
        }
        return true;
    }

    private void broadcast(Message.MESSAGE_TYPE type, final Block block) {
        peers.forEach(peer -> sendMessage(type, peer.getAddress(), peer.getPort(), block));
    }

//    private void sendMessage(Message.MESSAGE_TYPE type, String host, int port, Block... blocks) {
//        try (
//                final Socket peer = new Socket(host, port);
//                final ObjectOutputStream out = new ObjectOutputStream(peer.getOutputStream());
//                final ObjectInputStream in = new ObjectInputStream(peer.getInputStream())) {
//            Object fromPeer;
//            while ((fromPeer = in.readObject()) != null) {
//                if (fromPeer instanceof Message) {
//                    final Message msg = (Message) fromPeer;
//                    System.out.println(String.format("%d received: %s", this.port, msg.toString()));
//                    if (READY == msg.type) {
//                        out.writeObject(new Message.MessageBuilder()
//                                .withType(type)
//                                .withReceiver(port)
//                                .withSender(this.port)
//                                .withBlocks(Arrays.asList(blocks)).build());
//                    } else if (RSP_ALL_BLOCKS == msg.type) {
//                        if (!msg.blocks.isEmpty() && this.blockchain.size() == 1 && isChainValid(msg.blocks)) {
//                            blockchain = new ArrayList<>(msg.blocks);
//                        }
//                        break;
//                    }
//                }
//            }
//        } catch (UnknownHostException e) {
//            log.error(String.format("Unknown host %s %d", host, port));
//        } catch (IOException e) {
//            log.error((String.format("%s couldn't get I/O for the connection to %s. Retrying...%n", getPort(), port)));
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException ie) {
//                log.error(ie);
//            }
//        } catch (ClassNotFoundException e) {
//            log.error(e);
//        }
//    }

        private void sendMessage(Message.MESSAGE_TYPE type, String host, int port, Object object) {
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
                        if (object != null) {
                            if (object instanceof Block) {
                                out.writeObject(new Message.MessageBuilder()
                                        .withType(type)
                                        .withReceiver(port)
                                        .withSender(this.port)
                                        .withBlocks(Arrays.asList((Block) object)).build());
                            } else {
                                out.writeObject(new Message.MessageBuilder()
                                        .withType(type)
                                        .withReceiver(port)
                                        .withSender(this.port)
                                        .withTransactions(Arrays.asList((Transaction) object)).build());
                            }
                        } else {
                            out.writeObject(new Message.MessageBuilder()
                                    .withType(type)
                                    .withReceiver(port)
                                    .withSender(this.port)
                                    .withBlocks(Arrays.asList((Block) object)).build());
                        }
                    } else if (RSP_ALL_BLOCKS == msg.type) {
                        if (!msg.blocks.isEmpty() && this.blockchain.size() == 1 && isChainValid(msg.blocks)) {
                            blockchain = new ArrayList<>(msg.blocks);
                        }
                        break;
                    }
                }
            }
        } catch (UnknownHostException e) {
            log.error(String.format("Unknown host %s %d", host, port));
        } catch (IOException e) {
            log.error((String.format("%s couldn't get I/O for the connection to %s. Retrying...%n", getPort(), port)));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ie) {
                log.error(ie);
            }
        } catch (ClassNotFoundException e) {
            log.error(e);
        }
    }


    private boolean isChainValid(List<Block> blocks) {
        String previousHash = "ROOT_HASH";
        for (Block block : blocks) {
            if (!block.isSignVerified()) {
                return false;
            }

            if (!block.getPreviousHash().equals(previousHash)) {
                return false;
            }

            if (block.getTransaction() != null && !block.getTransaction().isSignVerified()) {
                return false;
            }

            previousHash = block.getHash();
        }
        return true;
    }

    public PrivateKey parsedPrivateKey() {
        return RSAUtil.parsePrivateKey(privateKey);
    }

}