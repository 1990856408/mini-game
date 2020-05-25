package com.mini.tool;

/**
 * 自定义线程异常捕获器
 */
public final class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
//        System.out.println("caught: " + e);
    }
}
