package cn.edu.ecnu.blockchain.util;

import cn.edu.ecnu.blockchain.agent.Block;

import java.security.PublicKey;
import java.util.List;

public class AccountUtil {
    public static double getAvailableAccount(List<Block> blockchain, String owner) {
        double account = 0;
        for (Block block : blockchain) {
            if (block.getCreatorPublicKey().equals(owner)) {
                account += 10;
            }
            if (block.getTransaction() != null) {
                if (block.getTransaction().getReceiver().equals(owner)) {
                    account += block.getTransaction().getValue();
                }
                if (block.getTransaction().getSender().equals(owner)) {
                    account -= block.getTransaction().getValue();
                }
            }
        }
        return account;
    }
}
