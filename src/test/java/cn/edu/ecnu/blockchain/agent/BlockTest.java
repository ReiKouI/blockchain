package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.util.RSAUtil;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockTest {

    @Test
    void isValid() {
        KeyPair sender = RSAUtil.createKeyPair();
        KeyPair receiver = RSAUtil.createKeyPair();
        Transaction transaction = new Transaction(
                RSAUtil.encodeKey(sender.getPublic()), RSAUtil.encodeKey(receiver.getPublic()), 8);
        transaction.sign(RSAUtil.encodeKey(sender.getPrivate()));
        Block block = new Block(0, "INIT_HASH", "Unit Test",
                RSAUtil.encodeKey(sender.getPublic()), transaction);
        block.sign(RSAUtil.encodeKey((sender.getPrivate())));
        assertTrue(block.isSignVerified());
    }
}