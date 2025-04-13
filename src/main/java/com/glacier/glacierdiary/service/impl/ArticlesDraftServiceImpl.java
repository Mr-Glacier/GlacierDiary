package com.glacier.glacierdiary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glacier.glacierdiary.configuration.security.JwtTokenUtil;
import com.glacier.glacierdiary.dao.ArticlesDraftMapper;
import com.glacier.glacierdiary.entity.ArticlesDraft;
import com.glacier.glacierdiary.service.ArticlesDraftService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote ArticlesDraftService 实现类
 * @since 2025/4/13 5:46
 */
@Service
public class ArticlesDraftServiceImpl extends ServiceImpl<ArticlesDraftMapper, ArticlesDraft> implements ArticlesDraftService {

    private final JwtTokenUtil jwtTokenUtil;

    public ArticlesDraftServiceImpl(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public ArticlesDraft addDraft(String token) {
        ArticlesDraft articlesDraft = new ArticlesDraft();
        articlesDraft.setId(UUID.randomUUID().toString());
        articlesDraft.setUserId(jwtTokenUtil.getUidFromToken(token.substring(7)));
        save(articlesDraft);
        return articlesDraft;
    }

    @Override
    public ArticlesDraft getDraftById(String id) {
        return getById(id);
    }
}
