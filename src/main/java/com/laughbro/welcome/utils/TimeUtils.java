package com.laughbro.welcome.utils;


import org.junit.Test;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用于时间的处理
 */
@Component
public class TimeUtils {



    /**
     * 获得当前时间返回string
     */
    public String timeGetNow(){
        //获得当前时间
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(currentDate);
        //System.out.println(formattedDate);
        return formattedDate;
    }


    /**
     * 比较两个时间的大小 1>=2 返回1  1<2 -1 格式错误0
     */
    public int timeCompare(String time1 ,String time2){
        //判断合法

        //比较大小
        return 0;
    }


    public  List<String> getDateRange(int day1, int day2) {
        List<String> dateList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 计算日期范围
        for (int i = day1; i <= day2; i++) {
            LocalDate date = currentDate.minusDays(i);
            String formattedDate = date.format(formatter);
            dateList.add(formattedDate);
        }

        return dateList;
    }


}
