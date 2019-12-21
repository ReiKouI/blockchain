package cn.edu.ecnu.blockchain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashUtilTest {

    @Test
    void getSha256() {
        String rawString = "123";
        String expect = "a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3";
        assertEquals(expect, HashUtil.getSha256(rawString));
    }

    @Test
    void matchDifficulty() {
        String hash = "ebef8hcb0epse3vTscxo3xi2ZbImviQEdNWCs06/0dquyaxPTnlQcAPsszt0DZf3gbhkY7n+3yQsO5QbWLPbHL28U" +
                "BD11zLz8KHkYMLaz1fkYxU57yhI7YEM4rD8yZPgGZYeUpN3K3C4csMiWZksxg6kKbnjenTMgnRZaQfqN2UfRwKv9AWfmxhw" +
                "COe/tk/8HzUKT2+KJyzHPIAMA/Tbfmjl2tKDww/N/rszM2UToH9pSvkIEQ5WnawCZbjBV7QiOH92i6/1dXMhzKST+o7MD2Z" +
                "E6PGSuaRHFvsjYKaZhxU3AjP3QGoHv43BMfj8vVtBfHmyY/pU+xlktMPMryNIHg==";
        assertTrue(HashUtil.matchDifficulty(hash, 1));
    }
}