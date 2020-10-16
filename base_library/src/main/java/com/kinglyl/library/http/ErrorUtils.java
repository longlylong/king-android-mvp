package com.kinglyl.library.http;

/**
 * 服务器访问错误语
 * Created by King on 2015/4/27 0027.
 */
public class ErrorUtils {

    public static String getErrorMsg() {
        return "网络无法连接或服务器无法连接！";
    }

    public static String getDbErrorMsg(String msg) {
        return msg + " (数据库异常，请清除数据)";
    }

}
