package com.zw.util;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
    public static final String DATE_YEAR = "yyyy";
    public static final String DATE_MONTH = "MM";
    public static final String DATE_YEAR_MONTH = "yyyy-MM";
    public static final String DATE_DAY = "dd";
    public static final String DATE_HOUR = "HH";
    public static final String DATE_MINUTE = "mm";
    public static final String DATE_HOUR_MINUTE = "HH:mm";
    public static final String DATE_SECONDES = "ss";
    public static final String DATE_FORMAT1 = "yyyy-MM-dd";
    public static final String DATE_FORMAT2 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_SSS = "yyyy-MM-dd HH:mm:ss|SSS";
    public static final String DATE_NOFUll_FORMAT = "yyyyMMdd";
    public static final String TIME_NOFUll_FORMAT = "yyyyMMddHHmmss";

    public DateFormatUtils() {
    }

    /** @deprecated */
    @Deprecated
    public static String formatString(String value) {
        String sReturn = "";
        if (value != null && !"".equals(value)) {
            if (value.length() == 14) {
                sReturn = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8) + " " + value.substring(8, 10) + ":" + value.substring(10, 12) + ":" + value.substring(12, 14);
                return sReturn;
            } else if (value.length() == 19) {
                sReturn = value.substring(0, 4) + value.substring(5, 7) + value.substring(8, 10) + value.substring(11, 13) + value.substring(14, 16) + value.substring(17, 19);
                return sReturn;
            } else {
                if (value.length() == 10) {
                    sReturn = value.substring(0, 4) + value.substring(5, 7) + value.substring(8, 10);
                }

                if (value.length() == 8) {
                    sReturn = value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
                }

                return sReturn;
            }
        } else {
            return sReturn;
        }
    }

    /** @deprecated */
    @Deprecated
    public static String formatDate(String date, String format) {
        if (date != null && !"".equals(date)) {
            Date dt = null;
            SimpleDateFormat inFmt = null;
            SimpleDateFormat outFmt = null;
            ParsePosition pos = new ParsePosition(0);
            date = date.replace("-", "").replace(":", "").replace(" ", "");
            if (date != null && !"".equals(date.trim())) {
                try {
                    if (Long.parseLong(date) == 0L) {
                        return "";
                    }
                } catch (Exception var8) {
                    return date;
                }

                try {
                    switch(date.trim().length()) {
                        case 6:
                            inFmt = new SimpleDateFormat("yyyyMM");
                            break;
                        case 7:
                        case 9:
                        case 11:
                        case 13:
                        default:
                            return date;
                        case 8:
                            inFmt = new SimpleDateFormat("yyyyMMdd");
                            break;
                        case 10:
                            inFmt = new SimpleDateFormat("yyyyMMddHH");
                            break;
                        case 12:
                            inFmt = new SimpleDateFormat("yyyyMMddHHmm");
                            break;
                        case 14:
                            inFmt = new SimpleDateFormat("yyyyMMddHHmmss");
                    }

                    if ((dt = inFmt.parse(date, pos)) == null) {
                        return date;
                    } else {
                        if (format != null && !"".equals(format.trim())) {
                            outFmt = new SimpleDateFormat(format);
                        } else {
                            outFmt = new SimpleDateFormat("yyyy年MM月dd日");
                        }

                        return outFmt.format(dt);
                    }
                } catch (Exception var7) {
                    return date;
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String formatDate(Date date, String format) {
        return formatDate(DateUtil.date2String(date), format);
    }

    /** @deprecated */
    @Deprecated
    public static String formatDate(String value) {
        return getFormat("yyyy-MM-dd HH:mm:ss").format(DateUtil.string2Date(value, "yyyy-MM-dd HH:mm:ss"));
    }

    /** @deprecated */
    @Deprecated
    public static String formatDate(Date value) {
        return formatDate(DateUtil.date2String(value));
    }

    /** @deprecated */
    @Deprecated
    protected static SimpleDateFormat getFormat(String format) {
        if (format == null || "".equals(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }

        return new SimpleDateFormat(format);
    }

    public static String dateStr(Date date, String formatStr) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            String str = format.format(date);
            return str;
        }
    }

    public static String dateStr(String dateStr, String formatStr) {
        if (dateStr == null) {
            return "";
        } else {
            Date date = DateUtil.string2Date(dateStr);
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            String str = format.format(date);
            return str;
        }
    }

    public static String dateStr(Date date) {
        return dateStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateStr(String date) {
        return dateStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateStr2(Date date) {
        return dateStr(date, "yyyy-MM-dd");
    }

    public static String dateStr2(String date) {
        return dateStr(date, "yyyy-MM-dd");
    }

    public static void main(String[] args) {
        System.out.println(dateStr2("2019-08-22 10:30:55"));
    }
}
