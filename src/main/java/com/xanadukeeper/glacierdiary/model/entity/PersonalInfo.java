package com.xanadukeeper.glacierdiary.model.entity;

import lombok.Data;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 博主信息实体类
 * @since 2024/10/7 21:47
 */
@Data
public class PersonalInfo {
    /**
     * 博主头像
     */
    private String avatar;
    /**
     * 博主昵称
     */
    private String nickname;
    /**
     * 博主简介
     */
    private String introduction;
    /**
     * 博主 github
     */
    private String github;
    /**
     * 博主邮箱
     */
    private String email;
    /**
     * 博客分类
     */
    private Integer categoryCount;
    /**
     * 博客标签
     */
    private Integer tagCount;
    /**
     * 博客数量
     */
    private Integer blogCount;
}
