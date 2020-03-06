package com.zw.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.NumberUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static String changeToFull(String str) {
        String source = "1234567890!@#$%^&*()abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_=+\\|[];:'\",<.>/?";
        String[] decode = new String[]{"１", "２", "３", "４", "５", "６", "７", "８", "９", "０", "！", "＠", "＃", "＄", "％", "︿", "＆", "＊", "（", "）", "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ", "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ", "ｙ", "ｚ", "Ａ", "Ｂ", "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ", "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ", "－", "＿", "＝", "＋", "＼", "｜", "【", "】", "；", "：", "'", "\"", "，", "〈", "。", "〉", "／", "？"};
        String result = "";

        for(int i = 0; i < str.length(); ++i) {
            int pos = source.indexOf(str.charAt(i));
            if (pos != -1) {
                result = result + decode[pos];
            } else {
                result = result + str.charAt(i);
            }
        }

        return result;
    }

    public static String unicodeEscaped(char ch) {
        if (ch < 16) {
            return "\\u000" + Integer.toHexString(ch);
        } else if (ch < 256) {
            return "\\u00" + Integer.toHexString(ch);
        } else {
            return ch < 4096 ? "\\u0" + Integer.toHexString(ch) : "\\u" + Integer.toHexString(ch);
        }
    }

    public static String toString(Object object, String nullStr) {
        return object == null ? nullStr : object.toString();
    }

    public static String repeatString(String value, int count) {
        if (value != null && !"".equals(value) && count > 1) {
            int length = value.length();
            if (length == 1) {
                return repeatChar(value.charAt(0), count);
            } else {
                int outputLength = length * count;
                switch(length) {
                    case 1:
                        return repeatChar(value.charAt(0), count);
                    case 2:
                        char ch0 = value.charAt(0);
                        char ch1 = value.charAt(1);
                        char[] output2 = new char[outputLength];

                        for(int i = count * 2 - 2; i >= 0; --i) {
                            output2[i] = ch0;
                            output2[i + 1] = ch1;
                            --i;
                        }

                        return new String(output2);
                    default:
                        StringBuilder buf = new StringBuilder(outputLength);

                        for(int i = 0; i < count; ++i) {
                            buf.append(value);
                        }

                        return buf.toString();
                }
            }
        } else {
            return value;
        }
    }

    public static String repeatChar(char ch, int count) {
        char[] buf = new char[count];

        for(int i = count - 1; i >= 0; --i) {
            buf[i] = ch;
        }

        return new String(buf);
    }

    public static boolean isAllLowerCase(String value) {
        if (value != null && !"".equals(value)) {
            for(int i = 0; i < value.length(); ++i) {
                if (!Character.isLowerCase(value.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static boolean isAllUpperCase(String value) {
        if (value != null && !"".equals(value)) {
            for(int i = 0; i < value.length(); ++i) {
                if (!Character.isUpperCase(value.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public static String reverse(String value) {
        return value == null ? null : (new StringBuffer(value)).reverse().toString();
    }

    public static String subString(String resourceString, int length) {
        String resultString = "";
        if (resourceString != null && !"".equals(resourceString) && length >= 1) {
            if (resourceString.length() < length) {
                return resourceString;
            } else {
                char[] chr = resourceString.toCharArray();
                int strNum = 0;
                int strGBKNum = 0;
                boolean isHaveDot = false;

                for(int i = 0; i < resourceString.length(); ++i) {
                    if (chr[i] >= 161) {
                        strNum += 2;
                        ++strGBKNum;
                    } else {
                        ++strNum;
                    }

                    if (strNum == length || strNum == length + 1) {
                        if (i + 1 < resourceString.length()) {
                            isHaveDot = true;
                        }
                        break;
                    }
                }

                resultString = resourceString.substring(0, strNum - strGBKNum);
                if (isHaveDot) {
                    resultString = resultString + "...";
                }

                return resultString;
            }
        } else {
            return resourceString;
        }
    }

    public static String subHTMLString(String htmlString, int length) {
        return subString(delHTMLTag(htmlString), length);
    }

    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
        String regEx_html = "<[^>]+>";
        String regEx_space = "\\s*|\t|\r|\n";
        Pattern p_script = Pattern.compile(regEx_script, 2);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        Pattern p_style = Pattern.compile(regEx_style, 2);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        Pattern p_html = Pattern.compile(regEx_html, 2);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        Pattern p_space = Pattern.compile(regEx_space, 2);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isAnyEmpty(String... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return true;
        } else {
            String[] var1 = strs;
            int var2 = strs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String str = var1[var3];
                if (isEmpty(str)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isAllEmpty(String... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return true;
        } else {
            String[] var1 = strs;
            int var2 = strs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String str = var1[var3];
                if (isNotEmpty(str)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isAllNotEmpty(String... strs) {
        if (ArrayUtils.isEmpty(strs)) {
            return false;
        } else {
            String[] var1 = strs;
            int var2 = strs.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                String str = var1[var3];
                if (isEmpty(str)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String getNumber(String str) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean isMobilePhone(String value) {
        String mobilePhonePattern = "^((1[0-9]))\\d{9}$";
        Pattern p = Pattern.compile(mobilePhonePattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean isNumber(String value) {
        String mobilePhonePattern = "^[1-9]\\d*$";
        Pattern p = Pattern.compile(mobilePhonePattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public static boolean equals(String text1, String text2) {
        if (text1 == null) {
            text1 = "";
        }

        if (text2 == null) {
            text2 = "";
        }

        return text1.trim().equalsIgnoreCase(text2.trim());
    }

    public static int parseInteger(String value) {
        return NumberUtils.parseNumber(value, Number.class).intValue();
    }


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
