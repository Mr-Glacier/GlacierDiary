package com.glacier.glacierdiary.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 登录接口DTO
 * @since 2025/4/13 3:11
 */
@ApiModel(description = "登录接口DTO", value = "登录接口DTO")
public class LoginDTO {
    @ApiModelProperty(value = "用户账户名称", required = true, example = "glacier")
    private String username;
    @ApiModelProperty(value = "用书输入密码", required = true, example = "glacier")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
