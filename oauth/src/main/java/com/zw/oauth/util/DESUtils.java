package com.zw.oauth.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class DESUtils {
    protected static final String KEY = "ScAKC0XhadTHT3Al0QIDAQAB";

    DESUtils() {
    }

    protected static String encrypt(String data, String key) {
        String encryptedData ;

        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, secretKey, sr);
            encryptedData = Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
            return encryptedData;
        } catch (Exception var8) {
            throw new RuntimeException("加密错误，错误信息：", var8);
        }
    }

    protected static String decrypt(String cryptData, String key) {
        String decryptedData ;

        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, secretKey, sr);
            decryptedData = new String(cipher.doFinal(Base64.getDecoder().decode(cryptData)));
            return decryptedData;
        } catch (Exception var8) {
            throw new RuntimeException("解密错误，错误信息：", var8);
        }
    }
}
