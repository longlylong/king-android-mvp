package com.kinglyl.android.db;


import com.kinglyl.library.db.BaseStore;
import com.kinglyl.library.db.CommandQueueRunner;

import java.util.List;

public class TestDBStore extends BaseStore<TestDBModel> {


    public void save() {
        CommandQueueRunner.getInstance().putCommand(() -> {
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
