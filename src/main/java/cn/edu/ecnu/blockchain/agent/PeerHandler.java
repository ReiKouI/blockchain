package cn.edu.ecnu.blockchain.agent;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static cn.edu.ecnu.blockchain.agent.Message.MESSAGE_TYPE.*;


@Log4j2
public class PeerHandler extends Thread {
    private Socket client;
    private final Agent agent;

    PeerHandler(final Agent agent, final Socket client) {
        super(agent.getName() + System.currentTimeMillis());
        this.agent = agent;
        this.client = client;
    }

    @Override
    public void run() {
        try (
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                final ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
            Message message = new Message.MessageBuilder().withSender(agent.getPort()).withType(READY).build();
            out.writeObject(message);
            Object fromClient;
            while ((fromClient = in.readObject()) != null) {
                if (fromClient instanceof Message) {
                    final Message msg = (Message) fromClient;
                    log.info(String.format("%d received: %s", agent.getPort(), fromClient.toString()));
                    if (INFO_NEW_BLOCK == msg.type) {
                        if (msg.blocks.size() != 1) {
                            System.err.println("Invalid block received: " + msg.blocks);
                        }
                        synchronized (agent) {
                            agent.addBlockFromOtherAgent(msg.blocks.get(0));
                        }
                        break;
                    } else if (REQ_ALL_BLOCKS == msg.type) {
                        out.writeObject(new Message.MessageBuilder()
                                .withSender(agent.getPort())
                                .withType(RSP_ALL_BLOCKS)
                                .withBlocks(agent.getBlockchain())
                                .build());
                        break;
                    } else if (INFO_NEW_TRANSACTION == msg.type) {
                        if (msg.transactions.size() != 1) {
                            System.err.println("Invalid block received: " + msg.transactions);
                        }
                        synchronized (agent) {
                            agent.addTransaction(msg.transactions.get(0));
                        }
                        break;
                    } else if (INVALID_TRANSACTION == msg.type) {
                        if (msg.transactions.size() != 1) {
                            System.err.println("Invalid block received: " + msg.transactions);
                        }
                        synchronized (agent) {
                            agent.invalidTransaction(msg.transactions.get(0));
                        }
                        break;
                    }
                }
            }
            client.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}