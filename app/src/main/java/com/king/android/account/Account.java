package com.king.android.account;

import android.text.TextUtils;

import com.king.android.db.TestDBStore;

import lombok.Data;

@Data
public class Account {

    public static String GUEST = "guest";

    private String userId;
    private String token;

    public TestDBStore testDBStore = new TestDBStore();

    public boolean isLogin() {
        return !GUEST.equals(userId);
    }

    public String getDbName() {
        return "db_" + userId;
    }

}
