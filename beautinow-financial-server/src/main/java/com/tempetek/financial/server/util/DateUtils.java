package com.tempetek.financial.server.util;

import com.tempetek.maviki.entities.Report;
import com.tempetek.maviki.entities.User;
import com.tempetek.maviki.util.ReportUtil;
import com.tempetek.maviki.web.Echarts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class DateUtils {

    public static Long preDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH) - 1,0,0,0);
        long tt = calendar.getTime().getTime() / 1000;
        return tt;
    }

    public static Long nextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH) - 1,23,59,59);
        long tt = calendar.getTime().getTime() / 1000;
        return tt;
    }

    public static Long defaultTime() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse("2021-01-01 00:00:01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long timestamp = calendar.getTimeInMillis();
        return timestamp / 1000;
    }

    /*public static void main(String[] args) throws ParseException {
        preDay();
        nextDay();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse("2021-08-01 00:00:01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long timestamp = calendar.getTimeInMillis();
        System.out.println(timestamp / 1000);
    }*/

    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    public static List<String> getWeekDays(int year, int week) {
        // 计算目标周数
        if (week > 52) {
            year++;
            week -= 52;
        } else if (week <= 0) {
            year--;
            week += 52;
        }

        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();

        // 设置每周的开始日期
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date beginDate = cal.getTime();
        //String beginDate = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, 6);
        //String endDate = sdf.format(cal.getTime());
        Date endDate = cal.getTime();
        dateList.add(sdf.format(cal.getTime()));

        while (beginDate.before(cal.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, -1);
            dateList.add(sdf.format(cal.getTime()));
        }
        Collections.reverse(dateList);
        return dateList;
    }

    public static List<String> getMonthFullDay(int year , int month){
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        List<String> fullDayList = new ArrayList<>(32);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month-1 );
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }

    public static List<String> getAllMoth(int year){
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM");
        List<String> fullDayList = new ArrayList<>(12);
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, 0);
        for (int j = 1; j <= 12 ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.MONTH,1);
        }
        return fullDayList;
    }

    public static void main(String[] args) {
        System.out.println(getAllMoth(2021));
    }
}
