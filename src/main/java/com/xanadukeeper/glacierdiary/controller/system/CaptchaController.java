package com.xanadukeeper.glacierdiary.controller.system;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.xanadukeeper.glacierdiary.common.result.Result;
import com.xanadukeeper.glacierdiary.service.basicServices.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 验证码控制类
 * @since 2025/1/20 1:33
 */
@RestController
@Api(tags = "验证码模块")
@RequestMapping("/api/captcha")
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

            // 生成图片验证码
            LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 60, 4, 6);

            // 转换成为 base64
            ImageIO.write(lineCaptcha.getImage(), "png", baos);
            String base64Img = Base64.getEncoder().encodeToString(baos.toByteArray());

            // 生成唯一ID
            String captchaId = UUID.randomUUID().toString();

            // 将图片验证码的文本保存到 Redis 中，并设置过期时间为5分钟（300,000毫秒）
            redisService.set(captchaId, lineCaptcha.getCode(), 300000);

            Map<String, String> map = new HashMap<>(2);
            map.put("captchaId", captchaId);
            map.put("image", "data:image/png;base64," + base64Img);
            return Result.success(map);
        } catch (Exception e) {
            return Result.failed("验证码获取失败" + e.getMessage());
        }
    }

    @ApiOperation("验证码校验")
    @PostMapping("/checkCaptcha")
    public Result checkCaptcha(@RequestBody Map<String, String> captchaData) {
        String captcha = captchaData.get("captcha");
        String captchaId = captchaData.get("captchaId");
        String code = redisService.get(captchaId);
        if (code == null) {
            return Result.failed("验证码已过期");
        }
        if (code.equals(captcha)) {
            redisService.delete(captchaId);
            Map<String,Boolean> map = new HashMap<>();
            map.put("valid", true);
            return Result.success(map);
        } else {
            return Result.failed("验证码错误");
        }
    }


}
