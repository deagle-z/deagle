package com.zw.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    private static final String[] weeks = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};



    /**
     * 获取两个日期相差天数
     * @param date1
     * @param date2
     * @return
     */
    public static int GetDifferDays(String date1, String date2) {
        int days = 0;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fDate1 = sdf.parse(date1);
            Date fDate2 = sdf.parse(date2);
            days = (int) ((fDate1.getTime() - fDate2.getTime()) / (1000 * 3600 * 24));
        }catch (Exception e){
            e.printStackTrace();
        }
        return days;
    }


    /**
     * 获取指定日期与当前日期相差天数
     * @param date
     * @return
     */
    public static int GetDifferDays(String date) {
        return GetDifferDays(date, getCurrentTime());
    }


    public static long GetDifferSeconds(String date1, String date2) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        long returnSec = 0;
        try {
            date = dateFormat.parse(date1);
            long time1=date.getTime();
            date = dateFormat.parse(date2);
            long time2 = date.getTime();
            long compareDays = (time1 - time2) / 1000;
            returnSec=Long.parseLong(String.valueOf(compareDays));
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return returnSec;
    }

    /**
     * 获取指定天数之后(或之前)的日期
     * @date 2020/1/7
     * @param  date 指定时间
     * @param  i 偏移天数
     * @param  flag true=>往后 fales=>往前
     * @return Date
     */
    public static Date getDesignationDate(Date date,Integer i,boolean flag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        if(flag){
            calendar.set(Calendar.DATE, day + i);
        }else{
            calendar.set(Calendar.DATE, day - i);
        }
        return calendar.getTime();
    }



    /**
     * 获取指定天数之后(或之前)的日期
     * @date 2020/1/7
     * @param  date 指定时间
     * @param  i 偏移天数
     * @param  flag true=>往后 false=>往前
     * @return String
     */
    public static String getDesignationDateOfString(Date date,Integer i,boolean flag) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(getDesignationDate(date,i,flag));
    }




    /**
     * @Author Jerry
     * @Description 获取上周的开始时间
     * @Date 2019/8/15 17:34
     * @Param
     * @return
     * @Copyright(C) 2019
     */
    public static String getBeginDayOfLastWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return sdf.format(cal.getTime());
    }

    /**
     * @Author Jerry
     * @Description 获取上周的结束时间
     * @Date 2019/8/15 17:34
     * @Param
     * @return
     * @Copyright(C) 2019
     */
    public static String getEndDayOfLastWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.string2Date(getBeginDayOfLastWeek()));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return sdf.format(cal.getTime());
    }

    public static String getMonthToMinute(String appointmentDate){
        String currentMonth = String.valueOf(DateUtil.getCurrentMonth(appointmentDate)+1);
        if(currentMonth.length()==1){
            currentMonth="0"+currentMonth;
        }
        String currentHour = String.valueOf(getDayOfHour(appointmentDate));
        if (currentHour.length()==1) {
            currentHour = "0" + currentHour;
        }

        String currentMinute = String.valueOf(DateUtil.getCurrentMinute(appointmentDate));
        if (currentMinute.length()==1) {
            currentMinute = "0" + currentMinute;
        }

        String currentDay = String.valueOf(DateUtil.getCurrentDay(appointmentDate));
        if (currentDay.length()==1) {
            currentDay = "0" + currentDay;
        }
        return new StringBuilder()
                .append(currentMonth)
                .append("-")
                .append(currentDay)
                .append(" ")
                .append(currentHour)
                .append(":")
                .append(currentMinute).toString();
    }

    public static int getDayOfHour(String value) {
        Date date = string2Date(value, "HH");
        Calendar calendar = getCalendar(date, "HH");
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        Date date = new Date();
        return sdf.format(date);
    }

    public static String getCurrentTime() {
        return getCurrentTime("yyyy-MM-dd HH:mm:ss");
    }

    public static Date getCurrentDate(String format) {
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        String dateS = getCurrentTime(format);
        Date date = null;

        try {
            date = sdf.parse(dateS);
        } catch (ParseException var5) {
            var5.printStackTrace();
        }

        return date;
    }

    public static Date getCurrentDate() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    public static String addYearToDate(int year, Date date, String format) {
        Calendar calender = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calender.add(1, year);
        return sdf.format(calender.getTime());
    }

    public static String addYearToDate(int year, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = string2Date(date, format);
        }

        return addYearToDate(year, newDate, format);
    }

    public static String addMothToDate(int month, Date date, String format) {
        Calendar calender = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calender.add(2, month);
        return sdf.format(calender.getTime());
    }

    public static String addMothToDate(int month, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = string2Date(date, format);
        }

        return addMothToDate(month, newDate, format);
    }

    public static String addDayToDate(int day, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(5, day);
        return sdf.format(calendar.getTime());
    }

    public static String addDayToDate(int day, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = string2Date(date, format);
        }

        return addDayToDate(day, newDate, format);
    }

    public static String addHourToDate(int hour, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(10, hour);
        return sdf.format(calendar.getTime());
    }

    public static String addHourToDate(int hour, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = string2Date(date, format);
        }

        return addHourToDate(hour, newDate, format);
    }

    public static String addMinuteToDate(int minute, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(12, minute);
        return sdf.format(calendar.getTime());
    }

    public static String addMinuteToDate(int minute, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = string2Date(date, format);
        }

        return addMinuteToDate(minute, newDate, format);
    }

    public static String addSecondToDate(int second, Date date, String format) {
        Calendar calendar = getCalendar(date, format);
        SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
        calendar.add(13, second);
        return sdf.format(calendar.getTime());
    }

    public static String addSecondToDate(int second, String date, String format) {
        Date newDate = new Date();
        if (null != date && !"".equals(date)) {
            newDate = string2Date(date, format);
        }

        return addSecondToDate(second, newDate, format);
    }

    public static Calendar getCalendar(Date date, String format) {
        if (date == null) {
            date = getCurrentDate(format);
        }

        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender;
    }

    public static Date string2Date(String value) {
        if (value != null && !"".equals(value)) {
            SimpleDateFormat sdf = DateFormatUtils.getFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            Date date = null;

            try {
                value = DateFormatUtils.formatDate(value, "yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(value);
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return date;
        } else {
            return null;
        }
    }

    public static Date string2Date(String value, String format) {
        if (value != null && !"".equals(value)) {
            SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            Date date = null;

            try {
                value = DateFormatUtils.formatDate(value, format);
                date = sdf.parse(value);
            } catch (Exception var5) {
                var5.printStackTrace();
            }

            return date;
        } else {
            return null;
        }
    }

    public static String date2String(Date value, String format) {
        if (value == null) {
            return null;
        } else {
            SimpleDateFormat sdf = DateFormatUtils.getFormat(format);
            return sdf.format(value);
        }
    }

    public static String date2String(Date value) {
        if (value == null) {
            return null;
        } else {
            SimpleDateFormat sdf = DateFormatUtils.getFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(value);
        }
    }

    public static int getCurrentYear(Date value) {
        String date = date2String(value, "yyyy");
        return Integer.valueOf(date);
    }

    public static int getCurrentYear(String value) {
        Date date = string2Date(value, "yyyy");
        Calendar calendar = getCalendar(date, "yyyy");
        return calendar.get(1);
    }

    public static int getCurrentMonth(Date value) {
        String date = date2String(value, "MM");
        return Integer.valueOf(date);
    }

    public static int getCurrentMonth(String value) {
        Date date = string2Date(value, "MM");
        Calendar calendar = getCalendar(date, "MM");
        return calendar.get(2);
    }

    public static int getCurrentDay(Date value) {
        String date = date2String(value, "dd");
        return Integer.valueOf(date);
    }

    public static int getCurrentDay(String value) {
        Date date = string2Date(value, "dd");
        Calendar calendar = getCalendar(date, "dd");
        return calendar.get(5);
    }

    public static String getCurrentWeek(Date value) {
        Calendar calendar = getCalendar(value, "yyyy-MM-dd");
        int weekIndex = calendar.get(7) - 1 < 0 ? 0 : calendar.get(7) - 1;
        return weeks[weekIndex];
    }

    public static String getCurrentWeek(String value) {
        Date date = string2Date(value, "yyyy-MM-dd");
        return getCurrentWeek(date);
    }

    public static int getCurrentHour(Date value) {
        String date = date2String(value, "HH");
        return Integer.valueOf(date);
    }

    public static int getCurrentHour(String value) {
        Date date = string2Date(value, "HH");
        Calendar calendar = getCalendar(date, "HH");
        return calendar.get(5);
    }

    public static int getCurrentMinute(Date value) {
        String date = date2String(value, "mm");
        return Integer.valueOf(date);
    }

    public static int getCurrentMinute(String value) {
        Date date = string2Date(value, "mm");
        Calendar calendar = getCalendar(date, "mm");
        return calendar.get(12);
    }

    public static int getDifferenceTime(String startTime, String endTime, int type) {
        if (endTime == null || "".equals(endTime)) {
            endTime = getCurrentTime();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int value = 0;

        try {
            Date begin = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            long between = (end.getTime() - begin.getTime()) / 1000L;
            if (type == 1) {
                value = (int)(between / 864000L);
            } else if (type == 2) {
                value = (int)(between / 3600L);
            } else if (type == 3) {
                value = (int)(between / 60L);
            } else if (type == 4) {
                value = (int)between;
            }
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        return value;
    }

    public static int compareDate(String startDay, String endDay, int stype) {
        int n = 0;
        startDay = DateFormatUtils.formatDate(startDay, "yyyy-MM-dd");
        endDay = DateFormatUtils.formatDate(endDay, "yyyy-MM-dd");
        String formatStyle = "yyyy-MM-dd";
        if (1 == stype) {
            formatStyle = "yyyy-MM";
        } else if (2 == stype) {
            formatStyle = "yyyy";
        }

        endDay = endDay == null ? getCurrentTime("yyyy-MM-dd") : endDay;
        DateFormat df = new SimpleDateFormat(formatStyle);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        try {
            c1.setTime(df.parse(startDay));
            c2.setTime(df.parse(endDay));
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        while(!c1.after(c2)) {
            ++n;
            if (stype == 1) {
                c1.add(2, 1);
            } else {
                c1.add(5, 1);
            }
        }

        --n;
        if (stype == 2) {
            n /= 365;
        }

        return n;
    }

    public static int compareTime(String startTime, String endTime, int type) {
        if (endTime == null || "".equals(endTime)) {
            endTime = getCurrentTime();
        }

        SimpleDateFormat sdf = DateFormatUtils.getFormat("");
        int value = 0;

        try {
            Date begin = sdf.parse(startTime);
            Date end = sdf.parse(endTime);
            long between = (end.getTime() - begin.getTime()) / 1000L;
            if (type == 1) {
                value = (int)(between % 864000L / 3600L);
            } else if (type == 2) {
                value = (int)(between % 3600L / 60L);
            } else if (type == 3) {
                value = (int)(between % 60L / 60L);
            }
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        return value;
    }

    public static int compare(String date1, String date2, String format) {
        SimpleDateFormat df = DateFormatUtils.getFormat(format);

        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else {
                return dt1.getTime() < dt2.getTime() ? -1 : 0;
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return 0;
        }
    }

    public static String getMonthFirstDate(String date) {
        date = DateFormatUtils.formatDate(date);
        return DateFormatUtils.formatDate(date, "yyyy-MM") + "-01";
    }

    public static String getMonthLastDate(String date) {
        Date strDate = string2Date(getMonthFirstDate(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strDate);
        calendar.add(2, 1);
        calendar.add(6, -1);
        return DateFormatUtils.formatDate(calendar.getTime());
    }

    public static Date getWeekFirstDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int today = now.get(7);
        int first_day_of_week = now.get(5) + 2 - today;
        now.set(5, first_day_of_week);
        return now.getTime();
    }

    public static Date geWeektLastDate(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        int today = now.get(7);
        int first_day_of_week = now.get(5) + 2 - today;
        int last_day_of_week = first_day_of_week + 6;
        now.set(5, last_day_of_week);
        return now.getTime();
    }

    public static int daysBetween(String date1, String date2) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        try {
            Date d1 = sdf.parse(DateFormatUtils.dateStr2(date1));
            Date d2 = sdf.parse(DateFormatUtils.dateStr2(date2));
            cal.setTime(d1);
            long time1 = cal.getTimeInMillis();
            cal.setTime(d2);
            long time2 = cal.getTimeInMillis();
            return Integer.parseInt(String.valueOf((time2 - time1) / 86400000L));
        } catch (Exception var10) {
            var10.printStackTrace();
            return 0;
        }
    }
}
