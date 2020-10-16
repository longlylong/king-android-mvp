package com.kinglyl.library.account;


public class Account {

    static String GUEST = "guest";
    private String userId;
    private String token;

    public boolean isLogin() {
        return !GUEST.equals(userId);
    }

    public String getDbName() {
        return "db_" + userId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
