package com.mini.tool;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工场
 */
public class MiniThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
//        System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
//        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MiniUncaughtExceptionHandler());
//        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        return t;
    }
}
