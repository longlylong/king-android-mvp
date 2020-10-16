package com.kinglyl.android.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 测试模型
 * Created by King on 2015/8/20 0020.
 */

public class TestDBModel extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;

    public TestDBModel() {
        setId(RealmHelper.autoAdd(TestDBModel.class));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
