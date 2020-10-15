package com.simga.library.thread;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

public class AsyncExecutor implements IExecutor {

    private Executor mExecutor;

    private AsyncExecutor() {
        mExecutor = Executors.newCachedThreadPool(r -> new Thread(() -> {
            android.os.Process.setThreadPriority(THREAD_PRIORITY_BACKGROUND);
            r.run();
        }, "AsyncExecutor Runnable"));
    }

    public static AsyncExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void execute(@NonNull Runnable r) {
        mExecutor.execute(r);
    }

    @Override
    public void executeDelayed(final Runnable r, long delayed) {
        MainThreadExecutor.getInstance().executeDelayed(() -> execute(r), delayed);
    }

    private static class SingletonHolder {
        public static final AsyncExecutor INSTANCE = new AsyncExecutor();
    }
}
