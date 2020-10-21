package com.thatday.common.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {

    private static ThreadPoolUtil instance;

    private final ExecutorService service;

    private ThreadPoolUtil() {
        service = Executors.newFixedThreadPool(5);
    }

    public static ThreadPoolUtil getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolUtil.class) {
                if (instance == null) {
                    instance = new ThreadPoolUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 线程池执行任务
     */
    public void execute(Runnable runnable) {
        service.execute(runnable);
    }
}
