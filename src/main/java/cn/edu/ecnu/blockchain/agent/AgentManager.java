package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.util.RSAUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class AgentManager {

    private List<Agent> agents;
    private final Block root;

    public AgentManager() {
        KeyPair keyPair = RSAUtil.createKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        this.agents = new ArrayList<>();
        this.root = new Block(0, "ROOT_HASH", "ROOT", RSAUtil.encodeKey(publicKey), null);
        root.sign(RSAUtil.encodeKey(privateKey));
    }

    public Agent addAgent(String name, int port) {
        Agent a = new Agent(name, "localhost", port, root, agents);
        a.startHost();
        agents.add(a);
        return a;
    }

    public Agent getAgentByName(String name) {
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
        final Agent a = getAgentByName(name);
        if (a != null) {
            a.stopHost();
            agents.remove(a);
        }
    }

    public List<Block> getAgentBlockchain(String name) {
        final Agent agent = getAgentByName(name);
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

    public Block createBlock(final String name) {
        final Agent agent = getAgentByName(name);
        if (agent != null) {
            return agent.createBlock();
        }
        return null;
    }

    public Block createTransaction(String minerName, String senderName, String receiverName, double value) {
        final Agent miner = getAgentByName(minerName);
        final Agent sender = getAgentByName(senderName);
        final Agent receiver = getAgentByName(receiverName);
        if (miner != null && sender != null && receiver != null) {
            Transaction transaction = sender.createTransactionTo(receiver, value);
            return miner.createBlock(transaction);
        }
        return null;
    }
}