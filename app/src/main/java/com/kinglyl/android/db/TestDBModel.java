package com.kinglyl.android.db;

import com.kinglyl.library.db.RealmHelper;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;

/**
 * 测试模型
 * Created by King on 2015/8/20 0020.
 */

@Data
public class TestDBModel extends RealmObject {

    //自增id 不需要额外设置 构造方法需要调设置id的
    @PrimaryKey
    private int id;
    private String name;

    public TestDBModel() {
        setId(RealmHelper.autoAdd(TestDBModel.class));
    }

}
