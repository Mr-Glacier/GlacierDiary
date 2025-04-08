package com.glacier.glacierdiary.controller.system;

import com.glacier.glacierdiary.common.result.Result;
import com.glacier.glacierdiary.common.result.ResultCode;
import com.glacier.glacierdiary.service.basic.RedisService;
import com.glacier.glacierdiary.utils.CaptchaGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 验证码控制类
 * @since 2025/1/20 1:33
 */
@RestController
@Api(tags = "验证码模块")
@RequestMapping("/system/captcha")
public class CaptchaController {

    private final RedisService redisService;

    @Autowired
    public CaptchaController(RedisService redisService) {
        this.redisService = redisService;
    }

    /**
     * @apiNote 获取验证码
     */
    @ApiOperation("获取验证码")
    @GetMapping("/getCaptcha")
    public Result getCaptcha(HttpServletResponse response) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // 禁止缓存
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");

            Map<String, String> captcha = CaptchaGenerator.generateCaptcha();
            // 生成UID
            String captchaId = UUID.randomUUID().toString().replace("-", "");
            // redis存储验证码结果 ,并且5分钟过期
            redisService.set(captchaId, captcha.get("code"), 5 * 60 * 1000L);
            Map<String, String> result = new HashMap<>();
            result.put("code", captchaId);
            result.put("image", captcha.get("image"));
            return Result.success(result);
        } catch (Exception e) {
            return Result.failed(e.getMessage());
        }
    }

    @ApiOperation("验证码校验")
    @PostMapping("/checkCaptcha")
    public Result checkCaptcha(@ApiParam(value = "输入验证码", required = true) @RequestParam String captcha,
                               @ApiParam(value = "验证码ID", required = true) @RequestParam String captchaId) {
        String code = redisService.get(captchaId);
        if (code == null) {
            return Result.failed("验证码已过期");
        }
        if (code.equals(captcha)) {
            redisService.delete(captchaId);
            return Result.success();
        } else {
            return Result.failed("验证码错误");
        }
    }


}
