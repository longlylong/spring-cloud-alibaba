package com.thatday.common.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ThreadUtil extends cn.hutool.core.thread.ThreadUtil {

    //所有任务完成后执行
    public CompletableFuture<Void> allOf(List<CompletableFuture<?>> completableFutureList) {
        if (CollUtil.isEmpty(completableFutureList)) {
            return null;
        }
        return CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[]{}));
    }

    //其中一个任务完成后执行
    public CompletableFuture<Object> anyOf(List<CompletableFuture<?>> completableFutureList) {
        if (CollUtil.isEmpty(completableFutureList)) {
            return null;
        }
        return CompletableFuture.anyOf(completableFutureList.toArray(new CompletableFuture[]{}));
    }
}
