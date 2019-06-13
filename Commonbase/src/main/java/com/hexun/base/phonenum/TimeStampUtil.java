package com.hexun.base.phonenum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间戳格式化、比较、转化
 */

public class TimeStampUtil {
    // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
    public static String compareTime(String data) {
        // String time="1463126553000";
        // 获取当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date2 = formatter.format(currentTime);
        // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
        //String date1 = TimeStampUtil.formatData("yyyy-MM-dd HH:mm:ss", time);
        String timeLead = null;
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            Date d1 = df.parse(data);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别


            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);


            timeLead = "" + days + "天" + hours + "小时" + minutes + "分前";
        } catch (Exception e) {
        }

        return timeLead;
    }

    // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
    public static long compareDayTime(String data) {
        // String time="1463126553000";
        // 获取当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date2 = formatter.format(currentTime);
        // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
        //String date1 = TimeStampUtil.formatData("yyyy-MM-dd HH:mm:ss", time);
        long timeLead = 0;
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            Date d1 = df.parse(data);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别


            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);


            timeLead = days;
        } catch (Exception e) {
        }

        return timeLead;
    }

    // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
    public static long compareHourTime(String data) {
        // String time="1463126553000";
        // 获取当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date2 = formatter.format(currentTime);
        // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
        //String date1 = TimeStampUtil.formatData("yyyy-MM-dd HH:mm:ss", time);
        long timeLead = 0;
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            Date d1 = df.parse(data);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别


            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);


            timeLead = hours;
        } catch (Exception e) {
        }

        return timeLead;
    }
    // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
    public static long compareMiniteTime(String data) {
        // String time="1463126553000";
        // 获取当前时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date2 = formatter.format(currentTime);
        // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
        //String date1 = TimeStampUtil.formatData("yyyy-MM-dd HH:mm:ss", time);
        long timeLead = 0;
        // 计算的时间差
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        try {
            Date d1 = df.parse(data);
            Date d2 = df.parse(date2);
            long diff = d2.getTime() - d1.getTime();// 这样得到的差值是微秒级别


            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24))
                    / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours
                    * (1000 * 60 * 60))
                    / (1000 * 60);


            timeLead = hours;
        } catch (Exception e) {
        }

        return timeLead;
    }
    // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
    public static String formatData(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp * 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }

    // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
    public static String formatDatas(String dataFormat, long timeStamp) {
        if (timeStamp == 0) {
            return "";
        }
        timeStamp = timeStamp;//* 1000;
        String result = "";
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        result = format.format(new Date(timeStamp));
        return result;
    }
}
