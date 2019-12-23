package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.util.RSAUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

class TransactionTest {

    @Test()
    void valueTest() {
        KeyPair sender = RSAUtil.createKeyPair();
        KeyPair receiver = RSAUtil.createKeyPair();
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Transaction Transaction = new Transaction(
                    RSAUtil.encodeKey(sender.getPublic()), "sender",
                    RSAUtil.encodeKey(receiver.getPublic()), "receiver",
                    -1);
        });
    }

    @Test
    void isValid() {
        KeyPair sender = RSAUtil.createKeyPair();
        KeyPair receiver = RSAUtil.createKeyPair();
        Transaction Transaction = new Transaction(
                RSAUtil.encodeKey(sender.getPublic()), "sender",
                RSAUtil.encodeKey(receiver.getPublic()), "receiver",
                8);
        Transaction.sign(RSAUtil.encodeKey(sender.getPrivate()));
        Assertions.assertTrue(Transaction.isSignVerified());
        Transaction.sign(RSAUtil.encodeKey(receiver.getPrivate()));
        Assertions.assertFalse(Transaction.isSignVerified());
    }
}