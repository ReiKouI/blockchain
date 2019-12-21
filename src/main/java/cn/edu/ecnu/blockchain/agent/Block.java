package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.util.HashUtil;
import cn.edu.ecnu.blockchain.util.RSAUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Random;

@Data
@Log4j2
public class Block implements Serializable {
    public static final int DIFFICULTY = 1;
    private static final long serialVersionUID = 1L;

    private int index;
    private long nonce;
    private long timestamp;
    private String hash;
    private String previousHash;
    private String creator;
    private String creatorPublicKey;
    private Transaction transaction;

    // for jackson
    public Block() {
    }

    public Block(int index, String preHash, String creator, String creatorPublicKey, Transaction transaction) {
        this.index = index;
        this.timestamp = System.currentTimeMillis();
        this.previousHash = preHash;
        this.creator = creator;
        this.creatorPublicKey = creatorPublicKey;
        this.transaction = transaction;
    }

    public void sign(String privateKey) {
        do {
            nonce = new Random().nextLong();
            String rawString = getRawString();
            hash = RSAUtil.signData(rawString, RSAUtil.parsePrivateKey(privateKey));
        } while (!HashUtil.matchDifficulty(hash, Block.DIFFICULTY));
    }

    private String getRawString() {
        String trxStr = transaction == null ? null : transaction.toString();
        StringBuilder rawString = new StringBuilder();
        rawString.append(index)
                .append(nonce)
                .append(timestamp)
                .append(previousHash)
                .append(creator)
                .append(creatorPublicKey)
                .append(trxStr);
        return rawString.toString();
    }

    public boolean isSignVerified() {
        String rawString = getRawString();
        return RSAUtil.verifyData(rawString, hash, RSAUtil.parsePublicKey(creatorPublicKey));
    }
}