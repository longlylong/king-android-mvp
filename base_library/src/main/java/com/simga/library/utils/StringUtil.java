package com.simga.library.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 字符类型转换工具
 */
public class StringUtil {

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    public static String toString(Object obj) {
        if (obj == null)
            return "";
        else
            return obj.toString();
    }

    public static int toInt(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return -1;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }

    public static short toShort(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return -1;
        } else {
            return Short.parseShort(obj.toString());
        }
    }

    public static int toCount(Object obj) {
        if (obj == null || "".equals(obj) || "null".equals(obj)) {
            return 0;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }

    public static float toFloat(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        } else {
            return Float.parseFloat(obj.toString());
        }
    }

    public static double toDouble(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0;
        } else {
            return Double.parseDouble(obj.toString());
        }
    }

    /**
     * 将对象转换成Long型空对象默认装换成0
     *
     * @param obj
     * @return
     */
    public static Long toLong(Object obj) {
        if (obj == null || "".equals(obj)) {
            return 0L;
        } else {
            return Long.parseLong(obj.toString());
        }
    }

    /**
     * 将对象转换成boolean类型,默认为false
     *
     * @param obj
     * @return
     */
    public static Boolean toBoolean(Object obj) {
        if (obj == null || "".equals(obj)) {
            return false;
        }
        return Boolean.valueOf(obj.toString());
    }

    public static boolean checkStr(String str) {
        boolean bool = true;
        if (str == null || "".equals(str.trim()))
            bool = false;
        return bool;
    }

    public static String buildFirstChar(String str) {
        if (str == null)
            return null;
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static double double2point(double ff) {
        int j = (int) Math.round(ff * 10000);
        double k = (double) j / 100.00;

        return k;
    }

    public String snumberFormat(double unm) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(unm);
    }

    public static String delSpaceString(String d) {
        String ret = "";
        if (d != null) {
            ret = d.trim();
        }
        return ret;
    }

    /**
     * 数据定长输出
     *
     * @param pattern 长度及其格式（例如：定长�?0位，不足则前面补零，那么pattern�?0000000000"�?
     * @param number
     * @return
     */
    public static String getDecimalFormat(String pattern, String number) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(pattern);
        int num = Integer.parseInt(toInt(number) + "");
        if ((num + "").length() > pattern.length()) {
            String newNumber = (num + "").substring(0, pattern.length() - 1);
            num = Integer.parseInt(newNumber);
        }
        return myformat.format(num);
    }

    public static String formatDecimal(String pattern, int number) {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern(pattern);
        if ((number + "").length() > pattern.length()) {
            String newNumber = (number + "").substring(0, pattern.length() - 1);
            number = Integer.parseInt(newNumber);
        }
        return myformat.format(number);
    }

    public static String bytesToHexString(byte[] src, int length) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || length <= 0) {
            return null;
        }
        for (int i = 0; i < length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static float max(float... values) {
        float max = 0;
        for (float item : values) {
            if (max == 0) {
                max = item;
            } else {
                max = Math.max(max, item);
            }
        }
        return max;
    }

    public static float min(float... values) {
        float min = 0;
        for (float item : values) {
            if (min == 0) {
                min = item;
            } else {
                if (item == 0) {
                    continue;
                }
                min = Math.min(min, item);
            }
        }
        return min;
    }

    public static int level(float value, float level[]) {
        for (int i = 0; i < level.length; i++) {
            if (value < level[i]) {
                return i;
            }
        }
        return level.length;
    }


    /**
     * @param @param  number
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: topercent
     * @Description: 将double类型的数据改为百分数，默认显示两位小数
     */
    public static String toPercent(double number) {
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMinimumFractionDigits(2);
        return percentFormat.format(number);

    }

    public static String toNumberFormat(double number) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        return numberFormat.format(number);
    }

    public static boolean isMobile(String mobile) {
//        boolean isValid = false;
//        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
//        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
//        CharSequence inputStr = mobile + "";
//        Pattern pattern = Pattern.compile(expression);
//        Matcher matcher = pattern.matcher(inputStr);
//        Pattern pattern2 = Pattern.compile(expression2);
//        Matcher matcher2 = pattern2.matcher(inputStr);
//        if (matcher.matches() || matcher2.matches()) {
//            isValid = true;
//        }

        if (isMatchLength(mobile, 11)) {
            return true;
        }
        return false;
    }


    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */

    public static boolean judgePhoneNums(String phoneNums) {

        if (isMatchLength(phoneNums, 11)) {
            return true;
        }

        return false;

    }

    /**
     * 判断一个字符串的位数
     *
     * @param str
     * @param length
     * @return
     */

    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }


    /**
     * 验证手机格式
     */

    public static boolean isMobileNO(String mobileNums) {

        /*

         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188

		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）

		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9

		 */

        String telRegex = "[1][34578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

        if (TextUtils.isEmpty(mobileNums))

            return false;

        else

            return mobileNums.matches(telRegex);

    }



    public static boolean isPasswLength(String str) {
        if(str.length()>=6&&str.length()<16) {
            return true;
        }else {
            return false;
        }
    }

    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断是否是字母+数字
     *
     * @param number
     * @return
     */
    public static boolean isDigitalAndAlphabet(String number) {
        Pattern p1 = Pattern.compile("[0-9]*$");
        Pattern p2 = Pattern.compile("^[A-Za-z]+$");
        if (p1.matcher(number).matches() || p2.matcher(number).matches()) {
            return false;
        }
        return true;
    }

    /**
     * @param @param  phone
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: phone2Unknown
     * @Description: 将手机号中间4位变为****
     */
    public static String phone2Unknown(String phone) {
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4, phone.length());
    }

    /**
     * 将数据的后len位置為****
     *
     * @param data
     * @param len
     * @return
     */
    public static String parseToLastUnknown(String data, int len) {
        int count = data.length();
        StringBuffer sb = new StringBuffer();
        if (count > len) {
            for (int i = 0; i < len; i++) {
                sb.append("*");
            }
            return data.substring(0, count - len) + sb.toString();
        } else {
            return data;
        }

    }

    /**
     * 保留前len位
     *
     * @param data
     * @param len
     * @return
     */
    public static String remainFirstWords(String data, int len) {
        int count = data.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            sb.append("*");
        }
        if (count < len) {
            return data;
        } else {
            return data.substring(0, count - len) + sb.toString();
        }
    }


    /**
     * @param @param  data
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: to2Decimal
     * @Description: 保留两位小数
     */
    public static String to2Decimal(double data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#,##0.00";// 定义要显示的数字的格式
        df.applyPattern(style);
        return df.format(data);
    }

    /**
     * @param @param  data
     * @param @return 设定文件
     * @return double 返回类型
     * @throws
     * @Title: to2Double
     * @Description: 四舍五入，保留两位小数，返回double
     */
    public static double to2Double(double data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#0.00";// 定义要显示的数字的格式
        df.applyPattern(style);
        return StringUtil.toDouble(df.format(data));

    }

    public static String toFormatLong(long data) {
        DecimalFormat df = new DecimalFormat();
        String style = "#,###";// 定义要显示的数字的格式
        df.applyPattern(style);
        return df.format(data);
    }

    public static String toHundred(long data) {
        int remainder = (int) (data % 100 / 10);
        if (remainder >= 5) {
            return String.valueOf(((data / 100) + 1) * 100);
        } else {
            return String.valueOf(data / 100 * 100);
        }

    }

    /**
     * @param @param  value
     * @param @return 设定文件
     * @return int 返回类型
     * @throws
     * @Title: getStringLength
     * @Description: 获取字符的长度（中文算2，英文算1）
     */
    public static int getStringLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < value.length(); i++) {
            // 获取一个字符
            String temp = value.substring(i, i + 1);
            // 判断是否为中文字符
            if (temp.matches(chinese)) {
                // 中文字符长度为1
                valueLength += 2;
            } else {
                // 其他字符长度为0.5
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * MD5加密
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换数字为中文
     *
     * @param s
     * @return
     */
    public static String convertDecimal2Text(String s) {
        int i = toInt(s);
        String[] strs = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        if (i > 0 && i < strs.length) {
            return strs[i];
        }
        return strs[0];
    }

    /**
     * 转换数字为星期
     *
     * @param s
     * @return
     */
    public static String decimalStr2WeekdayStr(String s) {
        s = s.replace("1", "周一")
                .replace("2", "周二")
                .replace("3", "周三")
                .replace("4", "周四")
                .replace("5", "周五")
                .replace("6", "周六")
                .replace("7", "周日")
                .replace(",", "，");
        return s;
    }


    /**
     * 将50%转为0.5之类的
     *
     * @param s
     * @return
     */
    public static float percentToFloat(String s) {
        float result = 0;
        if (s != null && !"".equals(s) && s.endsWith("%") && s.length() > 1) {
            result = toFloat(s.substring(0, s.length() - 1)) / 100.0f;
        }
        return result;
    }


    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09"）返回时间戳
     *
     * @param time
     * @return
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        Date date = new Date();
        try {
            date = sdr.parse(time);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }


    // 定义script的正则表达式：<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>
    private final static Pattern htmlScriptPattern = Pattern.compile("<script[^>]*?>[\\s\\S]*?<\\/script>",
            Pattern.CASE_INSENSITIVE);

    // 定义style的正则表达式: <[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>
    private final static Pattern htmlStylePattern = Pattern.compile("<style[^>]*?>[\\s\\S]*?<\\/style>",
            Pattern.CASE_INSENSITIVE);

    // 定义HTML标签的正则表达式
    private final static Pattern htmlTagPattern = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);

    // 定义HTML中的转义字符,一些特殊字符处理，主要是&开头;结尾
    private final static Pattern htmlSpecialCharPattern = Pattern.compile("&[a-z]+;", Pattern.CASE_INSENSITIVE);
    /**
     * 对文本中的HTML标签和转义字符进行过滤，替换成指定的字符串
     *
     * @param htmlStr
     * @param replaceStr
     * @return
     */
    public static String html2Text(String htmlStr, String replaceStr) {
        String textStr = "";
        try {
            Matcher matcher = htmlScriptPattern.matcher(htmlStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤script标签

            matcher = htmlStylePattern.matcher(textStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤style标签

            matcher = htmlTagPattern.matcher(textStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤html标签

            matcher = htmlSpecialCharPattern.matcher(textStr);
            textStr = matcher.replaceAll(replaceStr); // 过滤特殊字符标签
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textStr;
    }
}
