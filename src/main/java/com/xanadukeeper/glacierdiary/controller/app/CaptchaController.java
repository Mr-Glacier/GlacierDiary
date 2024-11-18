package com.xanadukeeper.glacierdiary.controller.app;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 验证码控制类
 * @since 2024/10/1 15:01
 */
@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {

    @Autowired
    private JedisPool jedisPool; // Redis连接池

    /**
     * @apiNote 获取验证码,使用Hutool工具生成验证码 限制用户IP请求 ,一个小时超过 60 次则 禁止访问
     * @author Mr-Glacier
     */
    @GetMapping("/generateCaptcha")
    public void generateCaptcha(HttpServletResponse response,HttpServletRequest request) throws IOException {
        // 获取客户端IP地址
        String ipAddress = getIpAddress(request);

        // 检查该IP地址在一小时内是否已经超过最大请求次数
        if (isExceedMaxRequests(ipAddress)) {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Too many requests from this IP address.");
            return;
        }
        // 生成图片验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 60, 4,6);
        // 将图片验证码写入 Response输出流
        lineCaptcha.write(response.getOutputStream());
        // 生成唯一ID
        String captchaId = UUID.randomUUID().toString();
        try (Jedis jedis = jedisPool.getResource()) {
            // 将验证码答案存储到Redis中，并设置过期时间（例如5分钟）
            jedis.setex(captchaId, (int) TimeUnit.MINUTES.toSeconds(5), lineCaptcha.getCode());
        }
        // 设置响应头，让前端知道captchaId
        response.setHeader("Captcha-id", captchaId);
        // 增加该IP地址的请求计数
        incrementRequestCount(ipAddress);
    }

    /**
     * @apiNote 校验验证码
     * @author Mr-Glacier
     */
    @PostMapping("/validateCaptcha")
    public ResponseEntity<Map<String, Boolean>> validateCaptcha(@RequestBody Map<String, Object> requestBody) {
        String captcha = requestBody.get("captcha").toString();
        String captchaId = requestBody.get("captchaId").toString();
        System.out.println("captcha: " + captcha);
        System.out.println("captchaId: " + captchaId);
        boolean valid = false;

        try (Jedis jedis = jedisPool.getResource()) {
            // 从Redis中获取存储的验证码
            String storedCaptcha = jedis.get(captchaId);
            if (storedCaptcha != null && storedCaptcha.equalsIgnoreCase(captcha)) {
                valid = true;
                // 验证成功后删除Redis中的验证码
                jedis.del(captchaId);
            }
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", valid);

        return ResponseEntity.ok(response);
    }



    /**
     * @apiNote 获取IP地址
     * @author Mr-Glacier
     */
    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @apiNote 查询该IP地址在一小时内是否超过最大请求次数
     * @author Mr-Glacier
     */
    private boolean isExceedMaxRequests(String ipAddress) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "captcha:ip:" + ipAddress;
            long count = jedis.incr(key);
            if (count == 1) {
                // 第一次请求，设置过期时间为1小时
                jedis.expire(key, (int) TimeUnit.HOURS.toSeconds(1));
            }
            return count > 60; // 限制每小时最多10次请求
        }
    }

    /**
     * @apiNote 增加该IP地址的请求计数
     * @author Mr-Glacier
     */
    private void incrementRequestCount(String ipAddress) {
        try (Jedis jedis = jedisPool.getResource()) {
            String key = "captcha:ip:" + ipAddress;
            jedis.incr(key);
        }
    }
}
