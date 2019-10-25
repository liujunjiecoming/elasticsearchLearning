package com.jj.basic;

/**
 * @ClassName SingletonThread
 * @Description TODO
 * @Author JJLiu
 * @Date 19-10-18 下午2:46
 * @Version 1.0
 **/
public class SingletonThread implements Runnable {

    // 保证无法实例化 SingletonThread
    private SingletonThread() {
    }

    // 静态类保证thread的初始化是线程安全的，内部类实现了延迟加载的效果
    private static class SingletonThreadHolder
    {
        public static SingletonThread thread = new SingletonThread();
    }

    public static SingletonThread getInstance()
    {
        return SingletonThreadHolder.thread;
    }

    @Override
    public void run() {

    }



}
