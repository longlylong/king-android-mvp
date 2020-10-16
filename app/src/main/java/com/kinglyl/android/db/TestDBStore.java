package com.kinglyl.android.db;


import com.kinglyl.library.db.BaseStore;
import com.kinglyl.library.thread.DBQueueRunner;

import java.util.List;

public class TestDBStore extends BaseStore<TestDBModel> {

    public void save() {
        //数据库的操作需要运行的这个线程里面
        DBQueueRunner.getInstance().putCommand(() -> {
            TestDBModel testDBModel = new TestDBModel();
            testDBModel.setName("小明");
            saveOrUpdateModel(testDBModel);

            List<TestDBModel> all = getAll(TestDBModel.class);

            for (TestDBModel m : all) {
                m.setName("大明");
            }
            saveOrUpdateModel(all);

            List<TestDBModel> all2 = getAll(TestDBModel.class);
            System.out.println(all2);
        });
    }
}
