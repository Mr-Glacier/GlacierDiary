package com.xanadukeeper.glacierdiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote sss
 * @since 2024/10/1 21:08
 */
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "adminWeb/adminLogin";
    }

    @GetMapping("/adminWeb/adminRegister")
    public String showAdminRegisterPage() {
        // 返回注册页面的视图名称
        return "adminWeb/adminRegister"; // 假设这是注册页面的 Thymeleaf 模板文件名
    }

    @GetMapping("/adminWeb/adminLogin")
    public String showAdminLoginPage(){
        return "adminWeb/adminLogin";
    }
}