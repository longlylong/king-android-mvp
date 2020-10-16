package com.kinglyl.android.db;

import android.os.SystemClock;

import java.util.LinkedList;

/**
 * 背景线程命令队列
 */
public class CommandQueueRunner implements Runnable {
    private final LinkedList<Command> mCommands = new LinkedList<>();

    private CommandQueueRunner() {
        Thread mThread = new Thread(this);
        mThread.setName("CommandQueueRunner");
        mThread.setDaemon(true);
        mThread.start();
    }

    public static CommandQueueRunner getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void clearQueue() {
        mCommands.clear();
    }

    @Override
    public void run() {
        android.os.Process
                .setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
        while (true) {
            SystemClock.sleep(20);

            if (mCommands.size() > 0) {
                final Command command = mCommands.remove(0);
                if (command != null) {
                    RealmHelper.beginTransaction();
                    command.runnable.run();
                    RealmHelper.commitTransaction();
                }
            }
        }
    }

    public synchronized void putCommand(Runnable runnable) {
        Command command = new Command();
        command.runnable = runnable;
        command.description = "";
        mCommands.add(command);
    }

    public synchronized void putCommand(String description, Runnable runnable) {
        Command command = new Command();
        command.runnable = runnable;
        command.description = description;
        mCommands.add(command);
    }

    private static class SingletonHolder {
        public static final CommandQueueRunner INSTANCE = new CommandQueueRunner();
    }

    private static class Command {
        public Runnable runnable;
        public String description;
    }
}
