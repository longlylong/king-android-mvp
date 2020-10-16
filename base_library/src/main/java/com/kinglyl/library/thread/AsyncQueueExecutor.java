package com.kinglyl.library.thread;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 队列线程
 */
public class AsyncQueueExecutor implements IExecutor {
    private Executor mExecutor;

    private AsyncQueueExecutor() {
        mExecutor = Executors.newSingleThreadExecutor();
    }

    public static AsyncQueueExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void execute(@NonNull Runnable r) {
        mExecutor.execute(r);
    }

    @Override
    public void executeDelayed(final Runnable r, long delayed) {
        MainThreadExecutor.getInstance().executeDelayed(new Runnable() {
            @Override
            public void run() {
                execute(r);
            }
        }, delayed);
    }

    private static class SingletonHolder {
        public static final AsyncQueueExecutor INSTANCE = new AsyncQueueExecutor();
    }
}
