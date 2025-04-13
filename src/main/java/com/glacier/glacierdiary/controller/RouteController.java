package com.glacier.glacierdiary.controller;

import com.glacier.glacierdiary.entity.ArticlesDraft;
import com.glacier.glacierdiary.service.ArticlesDraftService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 全局路由控制类
 * @since 2025/3/31 21:22
 */
@Controller
public class RouteController {
    private final ArticlesDraftService articlesDraftService;

    public RouteController(ArticlesDraftService articlesDraftService) {
        this.articlesDraftService = articlesDraftService;
    }

    /**
     * 进入客户端首页
     */
    @GetMapping("/")
    public String home() {
        return "login";
    }

    /**
     * 进入登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 进入注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 进入后台管理首页
     */
    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin/home";
    }

    /**
     * 进入文章编辑页面
     */
    @GetMapping("/admin/article/edit")
    public String articleEdit(@RequestParam String id, @RequestParam String type, Model model) {
        ArticlesDraft articlesDraft = articlesDraftService.getDraftById(id);
        model.addAttribute("articlesDraft", articlesDraft);
        model.addAttribute("type", type);
        return "admin/article/edit";
    }

}
