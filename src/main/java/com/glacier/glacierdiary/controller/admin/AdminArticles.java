package com.glacier.glacierdiary.controller.admin;

import com.glacier.glacierdiary.common.result.Result;
import com.glacier.glacierdiary.entity.ArticlesDraft;
import com.glacier.glacierdiary.service.ArticlesDraftService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 文章核心控制类
 * @since 2025/4/13 4:55
 */
@RestController
@Api(tags = "文章模块")
@RequestMapping("/api/admin/article/draft")
public class AdminArticles {

    private final ArticlesDraftService articlesDraftService;
    public AdminArticles(ArticlesDraftService articlesDraftService) {
        this.articlesDraftService = articlesDraftService;
    }

    /**
     * @apiNote 新增文章[草稿]
     */
    @GetMapping("/add")
    public Result<Object> addDraft(@RequestHeader("Authorization") String token) {
        ArticlesDraft articlesDraft = articlesDraftService.addDraft(token);
        return Result.success(articlesDraft.getId());
    }

}
