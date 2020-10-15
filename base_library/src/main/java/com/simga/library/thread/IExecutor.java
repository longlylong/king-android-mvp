package com.simga.library.thread;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

public interface IExecutor extends Executor {

    void execute(@NonNull Runnable r);

    void executeDelayed(Runnable r, long delayed);
}
