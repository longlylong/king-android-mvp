package com.kinglyl.android.account;

import com.kinglyl.android.db.TestDBStore;

import lombok.Data;

@Data
public class Account {

    public static String GUEST = "guest";
    public TestDBStore testDBStore = new TestDBStore();
    private String userId;
    private String token;

    public boolean isLogin() {
        return !GUEST.equals(userId);
    }

    public String getDbName() {
        return "db_" + userId;
    }

}
