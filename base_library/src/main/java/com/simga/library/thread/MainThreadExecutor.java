package com.simga.library.thread;

import android.os.Looper;

import androidx.annotation.NonNull;


public class MainThreadExecutor implements IExecutor {
    private final WeakHandler mHandler;

    private MainThreadExecutor() {
        mHandler = new WeakHandler(Looper.getMainLooper());
    }

    public static MainThreadExecutor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public void execute(@NonNull Runnable r) {
        mHandler.post(r);
    }

    @Override
    public void executeDelayed(Runnable r, long delayed) {
        mHandler.postDelayed(r, delayed);
    }

    private static class SingletonHolder {
        public static final MainThreadExecutor INSTANCE = new MainThreadExecutor();
    }
}
