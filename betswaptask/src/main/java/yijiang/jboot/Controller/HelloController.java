package yijiang.jboot.Controller;


import io.jboot.app.JbootApplication;
import io.jboot.web.controller.JbootController;
import io.jboot.web.controller.annotation.RequestMapping;


@RequestMapping("/")
public class HelloController extends JbootController {

    public static void main(String[] args){
        JbootApplication.run(args);
    }
}
