package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.util.AccountUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AgentTest {

    @Test
    void createTransactionTo() throws InterruptedException {
        AgentManager manager = new AgentManager();
        Agent sender = manager.addAgent("sender", 3100);
        Agent receiver = manager.addAgent("receiver", 3101);
        Agent miner = manager.addAgent("miner", 3102);
        manager.createBlock(sender.getName());
        final Transaction transaction = manager.createTransaction(sender.getName(), receiver.getName(), 5.8);
        miner.createBlock(transaction);
        Assertions.assertEquals(4.2, AccountUtil.getAvailableAccount(sender.getBlockchain(), sender.getPublicKey()));
    }
}