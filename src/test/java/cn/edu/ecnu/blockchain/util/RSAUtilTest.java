package cn.edu.ecnu.blockchain.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

class RSAUtilTest {

    @Test
    void signDataTest() {
        KeyPair keyPair1 = RSAUtil.createKeyPair();
        KeyPair keyPair2 = RSAUtil.createKeyPair();
        String testData = "test Data sign by 1";
        String signature = RSAUtil.signData(testData, keyPair1.getPrivate());
        Assertions.assertTrue(RSAUtil.verifyData(testData,signature,keyPair1.getPublic()));
        Assertions.assertFalse(RSAUtil.verifyData(testData,signature,keyPair2.getPublic()));
    }
}