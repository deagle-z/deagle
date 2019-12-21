package com.zw.util;


import java.util.UUID;

/**
  * uuid 工具类
  * @date 2019/12/21
  * @author zw
*/
public class UUIDUtil {

    /**
      * 生成随机uuid
      * @date 2019/12/21
      * @return String
    */
    public static String getRandomId() {
        return UUID.randomUUID().toString();
    }
}
