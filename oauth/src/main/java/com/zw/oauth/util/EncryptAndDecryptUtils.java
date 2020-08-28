package com.zw.oauth.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class EncryptAndDecryptUtils {
    public EncryptAndDecryptUtils() {
    }

    public static String md5Encrypt(String value) {
        String result = null;
        if (value != null && !"".equals(value.trim())) {
            result = MD5Utils.encrypt(value, "MD5");
        }

        return result;
    }

    public static String shaEncrypt(String value) {
        String result = null;
        if (value != null && !"".equals(value.trim())) {
            result = MD5Utils.encrypt(value, "SHA1");
        }

        return result;
    }

    public static String base64Encrypt(String value) {
        String result = null;
        if (value != null && !"".equals(value.trim())) {
            result = Base64Utils.encrypt(value.getBytes());
        }

        return result;
    }

    public static String base64Decrypt(String value) {
        String result = null;

        try {
            if (value != null && !"".equals(value.trim())) {
                byte[] bytes = Base64Utils.decrypt(value);
                result = new String(bytes);
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return result;
    }

    public static String desEncrypt(String value, String key) {
        key = key == null ? "ScAKC0XhadTHT3Al0QIDAQAB" : key;
        String result = null;

        try {
            if (value != null && !"".equals(value.trim())) {
                result = DESUtils.encrypt(value, key);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static String desDecrypt(String value, String key) {
        key = key == null ? "ScAKC0XhadTHT3Al0QIDAQAB" : key;
        String result = null;

        try {
            if (value != null && !"".equals(value.trim())) {
                result = DESUtils.decrypt(value, key);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static String aesEncrypt(String value, String key) {
        key = key == null ? "NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0" : key;
        String result = null;

        try {
            if (value != null && !"".equals(value.trim())) {
                result = AESUtils.encrypt(value, key);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static String aesDecrypt(String value, String key) {
        key = key == null ? "NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0" : key;
        String result = null;

        try {
            if (value != null && !"".equals(value.trim())) {
                result = AESUtils.decrypt(value, key);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {
        String key = "HISUM@2020";
        JSONObject object = new JSONObject();
        object.put("registerType", "1");
        object.put("promotionUser", "U_M190600003_0001");
        String s = aesEncrypt(JSON.toJSONString(object), key);
        System.out.println(s);
        String s1 = aesDecrypt(s, key);
        System.out.println(s1);
    }
}
