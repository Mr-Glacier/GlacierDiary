package com.glacier.glacierdiary.entity.DTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote checkCaptcha DTO类
 * @since 2025/4/13 3:05
 */
@ApiModel(value = "验证码校验参数", description = "用于验证码校验的请求参数")
public class CheckCaptchaDTO {
    @ApiModelProperty(value = "用户输入的验证码内容", required = true, example = "7Yz9")
    private String captcha;
    @ApiModelProperty(value = "验证码ID（用于 Redis 存取）", required = true, example = "captcha:123456")
    private String captchaId;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    @Override
    public String toString() {
        return "CheckCaptchaDTO{" +
                "captcha='" + captcha + '\'' +
                ", captchaId='" + captchaId + '\'' +
                '}';
    }
}
