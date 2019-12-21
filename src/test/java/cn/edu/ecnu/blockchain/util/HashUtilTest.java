package cn.edu.ecnu.blockchain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HashUtilTest {

    @Test
    void getSha256() {
        String rawString = "123";
        String expect = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";
        assertEquals(expect, HashUtil.getSha256(rawString));
    }
}