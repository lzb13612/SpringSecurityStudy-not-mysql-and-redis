package com.sss.springsecuritystudy.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lzb
 * create time: 2022/6/20
 * 登录控制器
 */
@Controller
public class LoginController {

//    @RequestMapping("login")
//    public String login(){
//        System.out.println("执行登录方法");
//        return "redirect:main.html";
//    }
    @Secured("ROLE_abc")
    @RequestMapping("main")
    public String main(){
        return "redirect:main.html";
    }

    @RequestMapping("err")
    public String err(){
        return "redirect:error.html";
    }
}
