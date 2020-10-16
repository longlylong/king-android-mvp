package com.kinglyl.android.db;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class BaseStore<MODEL extends RealmObject> {

    private Class<MODEL> modelClass;

    public Realm getRealm() {
        return RealmHelper.getInstance().getRealm();
    }

    public List<MODEL> getAll() {
        RealmResults<MODEL> all = getRealm().where(modelClass).findAll();
        return getRealm().copyFromRealm(all);
    }

    public MODEL getOneById(int id) {
        return getRealm().where(modelClass).equalTo("id", id).findFirst();
    }

    //更新需要 事务
    public void updateModel(MODEL model) {
        getRealm().insertOrUpdate(model);
    }

    //更新需要 事务
    public void updateModel(List<MODEL> model) {
        getRealm().insertOrUpdate(model);
    }

    //删除需要 事务
    public void delModel(int id) {
        getOneById(id).deleteFromRealm();
    }

    //删除需要 事务
    public void delAllModel() {
        getRealm().where(modelClass).findAll().deleteAllFromRealm();
    }
}
