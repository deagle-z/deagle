package com.zw.oauth.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AESUtils {
    protected static final String KEY = "NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0";

    AESUtils() {
    }

    protected static String decrypt(String encryptValue, String key) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptValue), key);
    }

    protected static String encrypt(String value, String key) throws Exception {
        return base64Encode(aesEncryptToBytes(value, key));
    }

    private static String base64Encode(byte[] bytes) {
        return Base64Utils.encrypt(bytes);
    }

    private static byte[] base64Decode(String base64Code) throws Exception {
        byte[] var10000;
        if (base64Code == null) {
            var10000 = null;
        } else {
            new Base64Utils();
            var10000 = Base64Utils.decrypt(base64Code);
        }

        return var10000;
    }

    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }
}
