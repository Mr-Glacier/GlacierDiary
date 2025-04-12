package com.glacier.glacierdiary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 全局路由控制类
 * @since 2025/3/31 21:22
 */
@Controller
public class RouteController {

    /**
     * 进入客户端首页
     */
    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

}
