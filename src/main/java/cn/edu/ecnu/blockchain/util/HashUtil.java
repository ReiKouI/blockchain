package cn.edu.ecnu.blockchain.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.BitSet;

public class HashUtil {

    public static String getSha256(String input) {
        return DigestUtils.sha256Hex(input);
    }

    public static boolean matchDifficulty(String hash, int difficulty) {
        BitSet bitSet = bitSetFromByteArray(hash.getBytes());
        int count0inBitSet = count0inBitSet(bitSet);
        System.out.println(count0inBitSet);
        return count0inBitSet > difficulty + 680;
    }

    private static BitSet bitSetFromByteArray(byte[] bytes) {
        BitSet bits = new BitSet();
        for (int i = 0; i < bytes.length * 8; i++) {
            if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
                bits.set(i);
            }
        }
        return bits;
    }

    private static int count0inBitSet(BitSet bitSet) {
        int count = 0;
        for (int i = 0; i < bitSet.cardinality(); i++) {
            if (!bitSet.get(i)) {
                count++;
            }
        }
        return count;
    }
}
