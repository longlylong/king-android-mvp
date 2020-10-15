package com.simga.library.http;

/**
 * 服务器常量
 * Created by King on 2015/4/22 0022.
 */
public class HttpConstant {

    private static final String Online_Url = "";
    private static final String Test_Url = "http://192.168.0.66:7201/";

    /**
     * 线上服务器
     */
    private static boolean ONLINE_SERVER = false;

    public static String getRootApiUrl() {
        return ONLINE_SERVER ? Online_Url : Test_Url;
    }
}
