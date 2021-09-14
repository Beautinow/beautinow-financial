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

    public static void main(String[] args) {
        //System.out.println(getPastDate(1));

        List<User> userList = new ArrayList();
        userList.add(new User((String)null, "root", "", "", "", 1, 1, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 10:15:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "root", "", "", "", 1, 2, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 10:20:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "root", "", "", "", 2, 3, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 11:23:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "admin", "", "", "", 1, 4, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 11:25:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "admin", "", "", "", 2, 5, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 12:35:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "admin", "", "", "", 1, 6, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 13:14:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "test", "", "", "", 1, 7, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 13:26:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "test", "", "", "", 2, 8, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 13:37:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "test", "", "", "", 1, 9, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 13:45:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        userList.add(new User((String)null, "test", "", "", "", 2, 1, com.tempetek.maviki.util.DateUtil.parse("2021-09-12 15:11:39"), (Date)null, (String)null, (Date)null, (String)null, (Date)null, (String)null, (String)null, (String)null, (String)null));
        Date startTime = com.tempetek.maviki.util.DateUtil.parse("2021-09-06 00:00:00");
        Date endTime = com.tempetek.maviki.util.DateUtil.parse("2021-09-12 23:59:59");
        String[] groupFields = new String[]{"userName", "locked"};
        String template = "[userName]-[locked]";
        Report report = new Report("", 1, groupFields, "usable", 3, true, "expiredTime", 5, 1, template, (Object)null);
        Echarts echarts = Test.handler(startTime, endTime, userList, report);
        System.out.println(echarts);
    }

}
