package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.util.*;
import lombok.Data;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.util.Date;

@Data
@Log4j2
public class Transaction implements Serializable {
    private long timeStamp;
    private String sender;
    private String senderName;
    private String receiver;
    private String receiverName;
    private double value;
    private String signature;
    private boolean valid;

    public Transaction(String sender, String senderName, String receiver, String receiverName, double value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must not less than 0.");
        }
        this.timeStamp = new Date().getTime();
        this.sender = sender;
        this.senderName = senderName;
        this.receiver = receiver;
        this.receiverName = receiverName;
        this.value = value;
        this.valid = true;
    }

    private String getSignData() {
        return timeStamp + sender + receiver + value;
    }

    public void sign(String privateKey) {
        this.signature = RSAUtil.signData(getSignData(), RSAUtil.parsePrivateKey(privateKey));
    }

    public boolean isSignVerified() {
        return RSAUtil.verifyData(getSignData(), signature, RSAUtil.parsePublicKey(sender));
    }


}
