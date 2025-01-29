package com.xanadukeeper.glacierdiary.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 路由控制类,用于系统页面跳转
 * @since 2024/10/1 21:08
 */
@Controller
public class HomeController {
    /**
     * 进入客户端首页
     */
    @GetMapping("/")
    public String home() {
        return "client/home";
    }


    /**
     * 进入管理后台
     */
    @GetMapping("/admin")
    public String showAdminHomePage() {
        return "backStage/adminLogin";
    }


    @GetMapping("/adminWeb/adminRegister")
    public String showAdminRegisterPage() {
        // 返回注册页面的视图名称
        return "adminWeb/adminRegister"; // 假设这是注册页面的 Thymeleaf 模板文件名
    }

    @GetMapping("/adminWeb/adminLogin")
    public String showAdminLoginPage(){
        // 博客客户端登录
        return "adminWeb/adminLogin";
    }

    @GetMapping("/client/home")
    public String showClientHomePage(){
        // 博客客户端首页
        return "client/home";
    }
}
