package com.jnu.youownme;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class timeutils {
    private int id;
    private long time;
    private String text;
    private int type;
    private boolean lunar;
    private int color;
    //构造函数
    public timeutils() {
        this.time = timeutils.getCalendar(java.util.Calendar.getInstance().getTimeInMillis()).getTimeInMillis();
    }
    public static long setTimeToZero(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static void setCalendarTime(com.haibin.calendarview.Calendar calendar, long time) {
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTimeInMillis(time);

        calendar.setYear(calendarTime.get(Calendar.YEAR));
        calendar.setMonth(calendarTime.get(Calendar.MONTH) + 1);
        calendar.setDay(calendarTime.get(Calendar.DAY_OF_MONTH));
    }

    public static com.haibin.calendarview.Calendar getCalendar(long time) {
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTimeInMillis(time);

        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        calendar.setYear(calendarTime.get(Calendar.YEAR));
        calendar.setMonth(calendarTime.get(Calendar.MONTH) + 1);
        calendar.setDay(calendarTime.get(Calendar.DAY_OF_MONTH));
        return calendar;
    }

    public static String getFormatDate(String format,
                                       com.haibin.calendarview.Calendar calendar) {
            return String.format(format, calendar.getYear(),
                    calendar.getMonth(), calendar.getDay());

    }

    public static String getFormatDate(String format,  long time) {
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        timeutils.setCalendarTime(calendar, time);
        return getFormatDate(format, calendar);
    }


    public static long getDayscount(long target_days) {
        com.haibin.calendarview.Calendar now = timeutils.getCalendar(java.util.Calendar.getInstance().getTimeInMillis());
        com.haibin.calendarview.Calendar anni = timeutils.getCalendar(target_days);
        int distance = 0;
        distance = anni.differ(now);
        getFormatDate("yyyy-MM-dd HH:mm:ss",distance);
        return distance;
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyy-MM-dd").format(date);
    }

}
