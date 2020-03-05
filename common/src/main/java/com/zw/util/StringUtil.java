package com.hisum.hd.core.common.util;

import com.hisum.framework.common.util.base.StringUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils{

    /**
     * 隐私处理字符串
     * @param val
     * @return
     */
    public static String GetSecString(String val){
        if(isNotEmpty(val)){
            //手机号码
            if(isMobilePhone(val)){
                return val.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            }
            //邮箱
            else if(isEmail(val)){
                return val.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)","$1****$3$4");
            }
            else if(val.length() >= 2 && val.length() <= 4){
                return val.substring(0, 1) + "*" + val.substring(val.length() - 1, val.length());
            }
            else{
                return val.substring(0, 2) + "******" + val.substring(val.length() - 2, val.length());
            }
        }
        return val;
    }



    /**
     * 检测是否邮箱地址
     * @param val
     * @return
     */
    public static boolean isEmail(String val) {
        if(isNotEmpty(val)){
            String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
            return val.matches(regex);
        }
        return false;
    }

    /**
     * 判断字符串是否为URL
     * @param urls 需要判断的String类型url
     * @return true:是URL；false:不是URL
     */
    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式
        //对比
        Pattern pat = Pattern.compile(regex.trim());
        Matcher mat = pat.matcher(urls.trim());
        //判断是否匹配
        isurl = mat.matches();
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }


    /**
     * 去掉数字末尾多余的.与0
     * @param v
     * @return
     */
    public static String subZeroAndDot(double v){
        return subZeroAndDot(String.valueOf(v));
    }

    /**
     * 去掉字符末尾多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    /**
     * 版本号比较
     * @param v1
     * @param v2
     * @return 0代表相等，1代表左边大，-1代表右边大
     * Utils.compareVersion("1.0.358_20180820090554","1.0.358_20180820090553")=1
     */
    public static int compareVersionNo(String v1, String v2) {
        if (v1.equals(v2)) {
            return 0;
        }
        String[] version1Array = v1.split("[._]");
        String[] version2Array = v2.split("[._]");
        int index = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        long diff = 0;

        while (index < minLen
                && (diff = Long.parseLong(version1Array[index])
                - Long.parseLong(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Long.parseLong(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Long.parseLong(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    /**
     * 生成指定长度的随机数字字符串
     * @param nLength 指定长度
     * @return 随机数字字符串
     */
    public static String getRandomNumbers(int nLength) {
        Random rd = new Random();
        String temp = "";
        for (int i = 0; i < nLength; i++) {
            int ret = rd.nextInt(10);
            if (ret < 0) {
                ret = ret * (-1);
            }
            temp += ret;
        }
        return temp;
    }


    public static void main(String[] args) {
        System.out.println(StringUtil.compareVersionNo("3","2.1.1"));
    }

}
