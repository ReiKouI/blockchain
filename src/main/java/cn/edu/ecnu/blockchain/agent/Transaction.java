package cn.edu.ecnu.blockchain.agent;

import cn.edu.ecnu.blockchain.util.RSAUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.Date;

@Data
@Log4j2
public class Transaction implements Serializable {
    private long timeStamp;
    private String sender;
    private String receiver;
    private double value;
    private String signature;

    public Transaction(String sender, String receiver, double value) {
        if (value < 0) {
            throw new IllegalArgumentException("value must not less than 0.");
        }
        this.timeStamp = new Date().getTime();
        this.sender = sender;
        this.receiver = receiver;
        this.value = value;
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
