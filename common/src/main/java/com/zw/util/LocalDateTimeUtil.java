package com.zw.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * localDateTime时间工具类
 *
 * @author zw
 * @date 2020/8/27
 */
public class LocalDateTimeUtil {
   public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


   /**
    * 获取now 时分秒
    *
    *
    * @return LocalDateTime
    */
    public static LocalDateTime nowTime(){
        return LocalDateTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
    }


    /**
     * 获取now 时分秒 str
     *
     *
     * @return str
     */
    public static String nowStr(){
        return FORMATTER.format(nowTime());
    }

}
