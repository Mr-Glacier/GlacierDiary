package com.glacier.glacierdiary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glacier.glacierdiary.entity.ArticlesDraft;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote ArticlesDraft 接口
 * @since 2025/4/13 5:46
 */
public interface ArticlesDraftService extends IService<ArticlesDraft> {

    /**
     * 新增文章草稿
     *
     * @param token 用户token
     * @return 文章草稿
     */
    ArticlesDraft addDraft(String token);

    /**
     * 获取文章草稿
     *
     * @param id 文章草稿id
     * @return ArticlesDraft 文章草稿
     */
    ArticlesDraft getDraftById(String id);
}
