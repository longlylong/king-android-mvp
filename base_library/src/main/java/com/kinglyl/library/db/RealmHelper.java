package com.kinglyl.library.db;

import android.content.SharedPreferences;

import com.kinglyl.library.account.Account;
import com.kinglyl.library.account.AccountManager;
import com.kinglyl.library.base.BaseApp;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * 数据库配置
 * Created by King on 2017/9/4 0004.
 */

public class RealmHelper {

    private static RealmHelper Instance = new RealmHelper();

    private RealmHelper() {
        initRealm();
    }

    public static RealmHelper getInstance() {
        if (Instance == null) {
            synchronized (RealmHelper.class) {
                if (Instance == null) {
                    Instance = new RealmHelper();
                }
            }
        }
        return Instance;
    }

    public static void beginTransaction() {
        RealmHelper.getInstance().getRealm().beginTransaction();
    }

    public static void commitTransaction() {
        RealmHelper.getInstance().getRealm().commitTransaction();
    }

    public synchronized static int autoAdd(Class model) {
        Account account = AccountManager.getInstance().getAccount();
        String name = account.getUserId() + "_db_id_" + model.getSimpleName();
        SharedPreferences sp = BaseApp.getSp(name);
        int id = sp.getInt(name, 0);
        id = id + 1;
        sp.edit().putInt(name, id).apply();
        return id;
    }

    public static void logout() {
        Realm.getDefaultInstance().close();
        Instance = null;
    }

    private void initRealm() {
        Account account = AccountManager.getInstance().getAccount();
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(account.getDbName() + "_v1.realm").deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
    }

    public Realm getRealm() {
        return Realm.getDefaultInstance();
    }
}
