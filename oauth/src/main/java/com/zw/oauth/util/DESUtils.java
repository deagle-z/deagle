package com.zw.oauth.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtils {
    protected static final String KEY = "ScAKC0XhadTHT3Al0QIDAQAB";

    DESUtils() {
    }

    protected static String encrypt(String data, String key) {
        String encryptedData = null;

        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, secretKey, sr);
            encryptedData = (new BASE64Encoder()).encode(cipher.doFinal(data.getBytes()));
            return encryptedData;
        } catch (Exception var8) {
            throw new RuntimeException("加密错误，错误信息：", var8);
        }
    }

    protected static String decrypt(String cryptData, String key) {
        String decryptedData = null;

        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec deskey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, secretKey, sr);
            decryptedData = new String(cipher.doFinal((new BASE64Decoder()).decodeBuffer(cryptData)));
            return decryptedData;
        } catch (Exception var8) {
            throw new RuntimeException("解密错误，错误信息：", var8);
        }
    }
}
