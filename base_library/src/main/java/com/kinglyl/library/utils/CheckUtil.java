package com.kinglyl.library.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

    /**
     * 判断两个string是否相等
     */
    public static boolean checkEqual(String str1, String str2) {
        return !(str1 == null || str2 == null) && str1.equals(str2);
    }


    /**
     * 同时判断多个参数是否为空
     */
    public static boolean isNull(String... strArray) {
        for (String obj : strArray) {
            if (isNull(obj)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判是否是字母
     */
    public static boolean isLetter(String txt) {
        if (isNull(txt)) {
            return false;
        }
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher m = p.matcher(txt);
        return m.matches();
    }


    /**
     * 判断字符串对象是否为空
     */
    public static boolean isNull(String strObj) {
        return (strObj == null || "".equals(strObj));
    }

    /**
     * 判断是否邮箱
     */
    public static boolean checkEmail(Object strObj) {
        String str = strObj + "";
//        if (!str.endsWith(".com")) {
//            return false;
//        }
        String match = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern pattern = Pattern.compile(match);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断是否为电话号码
     */
    public static boolean checkPhone(Object phoneNumber) {
        boolean isValid = false;
//        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
//        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
//        CharSequence inputStr = phoneNumber + "";
//        Pattern pattern = Pattern.compile(expression);
//        Matcher matcher = pattern.matcher(inputStr);
//        Pattern pattern2 = Pattern.compile(expression2);
//        Matcher matcher2 = pattern2.matcher(inputStr);
//        if (matcher.matches() || matcher2.matches()) {
//            isValid = true;
//        }
//        String phone = inputStr+"";
//        if(!(phone.startsWith("13")||phone.startsWith("15")||phone.startsWith("17")||phone.startsWith("18"))){
//            isValid = false;
//        }

        if ((phoneNumber + "").length() >= 7) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 检查内容的长度是否大于等于要求
     */
    public static boolean checkLength(Object strObj, int length) {
        String str = strObj + "";
        return str.length() >= length;
    }

    /**
     * 检查字符串的长度
     */
    public static boolean checkLengthEq(Object strObj, int length) {
        String str = strObj + "";
        return str.length() == length;
    }

    /**
     * @Description: 检查是否为数字，以及这个数在min与max之间，包含min与max
     */
    public static boolean checkNum(Object strObj, int min, int max) {
        String str = strObj + "";
        try {
            int number = Integer.parseInt(str);
            return number <= max && number >= min;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @Description: 检查是否为数字，以及这个数在min与max之间，包含min与max,可以为小数
     */
    public static boolean checkNumWithDecimal(Object strObj, float min, float max) {
        String str = strObj + "";
        try {
            float number = Float.parseFloat(str);
            return number <= max && number >= min;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @Description: 检查字符串的长度范围是否符合要求
     */
    public static boolean checkLength(Object strObj, int start, int end) {
        String str = strObj + "";
        return str.length() >= start && str.length() <= end;
    }

    /**
     * @Description: 检查金额是否为数字以及是否为一个数的倍数
     */
    public static boolean checkMoney(Object strObj, int num) {
        String str = strObj + "";
        try {
            int money = Integer.parseInt(str);
            return money % num == 0;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 检查是否是网络链接
     */
    public static boolean checkURL(String url) {
        if (isNull(url)) {
            return false;
        }
        String regular = "(http|https)://[\\S]*";
        return Pattern.matches(regular, url);
    }

    public static boolean checkFileURL(String url) {
        if (isNull(url)) {
            return false;
        }
        String regular = "(file)://[\\S]*";
        return Pattern.matches(regular, url);
    }
}
