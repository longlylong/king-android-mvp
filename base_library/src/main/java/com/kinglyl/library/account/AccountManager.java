package com.kinglyl.library.account;

import com.kinglyl.library.db.RealmHelper;
import com.kinglyl.library.http.HttpProtocolFactory;
import com.kinglyl.library.utils.HawkKey;
import com.orhanobut.hawk.Hawk;

public class AccountManager {

    public static AccountManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Account getAccount() {
        String uid = Hawk.get(HawkKey.USER_ID, Account.GUEST);
        String token = Hawk.get(HawkKey.TOKEN, "");
        Account account = new Account();
        account.setUserId(uid);
        account.setToken(token);
        return account;
    }

    public void saveAccount(Account account) {
        Hawk.put(HawkKey.USER_ID, account.getUserId());
        Hawk.put(HawkKey.TOKEN, account.getToken());
    }

    public void logout() {
        Hawk.deleteAll();
        HttpProtocolFactory.logout();
        RealmHelper.logout();
    }

    private static class SingletonHolder {
        static final AccountManager INSTANCE = new AccountManager();
    }
}
