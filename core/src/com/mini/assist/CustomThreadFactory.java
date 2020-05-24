package com.mini.assist;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工场
 */
public final class CustomThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
//		System.out.println(this+" creating new Thread");
        Thread t = new Thread(r);
//		System.out.println("created "+t);
        t.setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler());
//		System.out.println("eh = "+t.getUncaughtExceptionHandler());
        return t;
    }
}
