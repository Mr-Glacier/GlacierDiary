package com.glacier.glacierdiary.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 草稿箱
 * @since 2025/4/13 5:34
 */
@TableName("articles_draft")
public class ArticlesDraft {

    /**
     * 主键ID，使用UUID
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;

    /**
     * 文章作者
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 文章标题
     */
    @TableField("title")
    private String title;

    /**
     * 文章分类
     */
    @TableField("category")
    private String category;

    /**
     * 文章标签，字符串存储 [1,5,6]
     */
    @TableField("tags")
    private String tags;

    /**
     * 封面图片URL
     */
    @TableField("cover")
    private String cover;

    /**
     * 文章内容
     */
    @TableField("article_content")
    private String articleContent;

    /**
     * 创建时间，插入时自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 更新时间，插入和更新时自动填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
