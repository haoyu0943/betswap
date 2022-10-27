package com.yijiang.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.yijiang.core.LoginInterceptor;
import com.yijiang.model.SysuserModel;
import com.yijiang.util.TmFunctions;

//团队成员工作导出
public class ExportController extends Controller {

    @Clear(LoginInterceptor.class)
    public void saveuser(){
        String str="123123";
        Thread thread = new MyThread2(str);
        thread.start();
        renderJson();
    }

    public void test(){
        System.out.println("++++++++");
    }
}
