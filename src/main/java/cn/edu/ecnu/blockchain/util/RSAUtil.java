package cn.edu.ecnu.blockchain.util;

import lombok.extern.log4j.Log4j2;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Log4j2
public class RSAUtil {

    public static final String SHA_256_WITH_RSA = "SHA256withRSA";
    public static final String RSA = "RSA";

    public static KeyPair createKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
            kpg.initialize(2048);
            return kpg.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.error(e);
            throw new RuntimeException("Could not create key pair", e);
        }
    }

    public static String signData(String plainText, PrivateKey privateKey) {
        try {
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(privateKey);
            privateSignature.update(plainText.getBytes(UTF_8));
            byte[] signature = privateSignature.sign();
            return Base64.getEncoder().encodeToString(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error(e);
            throw new RuntimeException("Could not sign data.", e);
        }
    }

    public static boolean verifyData(String plainText, String signature, PublicKey publicKey) {
        try {
            Signature publicSignature = Signature.getInstance(SHA_256_WITH_RSA);
            publicSignature.initVerify(publicKey);
            publicSignature.update(plainText.getBytes(UTF_8));
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return publicSignature.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            log.error(e);
            throw new RuntimeException("Could not sign data.", e);
        }
    }

    public static String encodeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static PublicKey parsePublicKey(String b64PublicKey) {
        byte[] bytes = Base64.getDecoder().decode(b64PublicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error(e);
            throw new RuntimeException("Could not parse key. The key should be base64-encoded.");
        }
    }

    public static PrivateKey parsePrivateKey(String b64PrivateKey) {
        byte[] bytes = Base64.getDecoder().decode(b64PrivateKey);
        PKCS8EncodedKeySpec specPvt = new PKCS8EncodedKeySpec(bytes);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(specPvt);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            log.error(e);
            throw new RuntimeException("Could not parse key. The key should be base64-encoded.");
        }
    }
}
