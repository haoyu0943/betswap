package com.yijiang.controller;

public class MyThread2  extends Thread{
    private String name;
    @Override
    public void run() {

        System.out.println("===="+name);


    }

    public MyThread2(String name)

    {
        this.name = name;
    }
}
