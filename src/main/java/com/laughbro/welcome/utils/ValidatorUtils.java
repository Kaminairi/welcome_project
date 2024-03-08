package com.laughbro.welcome.utils;


import org.junit.Test;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 【作用】 此类为校验工具用于校验输入值的正则表达
 */
@Component
public class ValidatorUtils {

    @Test
    public void test1(){
        BigInteger test= BigInteger.valueOf(202412312);
        System.out.println(validateNumber(String.valueOf(test)));
    }


    /**
     * 【作用】 校验是否为纯数字
     */
    public static boolean validateNumber(String input) {
        String pattern = "^[0-9]+$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }

    //
    /**
     * 【作用】 校验data格式  YYYY-MM-DD
     */
    public  boolean validateDate(String date) {
        String pattern = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(date);
        return matcher.matches();
    }

    //
    /**
     * 【作用】 校验datatime格式   YYYY-MM-DD HH:MM:SS
     */
    public  boolean validateDateTime(String dateTime) {
        String pattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(dateTime);
        return matcher.matches();
    }

    //


}
