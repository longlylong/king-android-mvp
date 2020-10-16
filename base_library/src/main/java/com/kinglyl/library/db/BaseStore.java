package com.kinglyl.library.db;


import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class BaseStore<MODEL extends RealmObject> {

    public Realm getRealm() {
        return RealmHelper.getInstance().getRealm();
    }

    public List<MODEL> getAll(Class<MODEL> modelClass) {
        RealmResults<MODEL> all = getRealm().where(modelClass).findAll();
        return getRealm().copyFromRealm(all);
    }

    public MODEL getOneById(Class<MODEL> modelClass, int id) {
        return getRealm().where(modelClass).equalTo("id", id).findFirst();
    }

    //更新需要 事务
    public void saveOrUpdateModel(MODEL model) {
        getRealm().insertOrUpdate(model);
    }

    //更新需要 事务
    public void saveOrUpdateModel(List<MODEL> model) {
        getRealm().insertOrUpdate(model);
    }

    //删除需要 事务
    public void delModel(Class<MODEL> modelClass, int id) {
        getOneById(modelClass, id).deleteFromRealm();
    }

    //删除需要 事务
    public void delAllModel(Class<MODEL> modelClass) {
        getRealm().where(modelClass).findAll().deleteAllFromRealm();
    }
}
