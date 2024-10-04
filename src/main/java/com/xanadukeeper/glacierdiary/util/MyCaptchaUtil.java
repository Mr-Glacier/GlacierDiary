//package com.xanadukeeper.glacierdiary.util;
//
//import cn.hutool.captcha.CaptchaUtil;
//import cn.hutool.captcha.LineCaptcha;
//import cn.hutool.core.lang.UUID;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author Mr-Glacier
// * @version 1.0
// * @apiNote 验证码工具类
// * @since 2024/10/1 15:36
// */
//@Component
//public class MyCaptchaUtil {
//
//    private final JedisPool jedisPool;
//
//    public MyCaptchaUtil(JedisPool jedisPool) {
//        this.jedisPool = jedisPool;
//    }
//
//    /**
//     * 生成验证码并返回唯一ID
//     *
//     * @param response HttpServletResponse
//     * @return 验证码唯一ID
//     * @throws IOException 如果写入响应失败
//     */
//    public String generateCaptcha(HttpServletResponse response) throws IOException {
//        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 60, 4,6);
//        lineCaptcha.write(response.getOutputStream());
//
//        // 生成唯一ID
//        String captchaId = UUID.randomUUID().toString();
//        try (Jedis jedis = jedisPool.getResource()) {
//            // 将验证码答案存储到Redis中，并设置过期时间（例如5分钟）
//            jedis.setex(captchaId, (int) TimeUnit.MINUTES.toSeconds(5), lineCaptcha.getCode());
//        }
//
//        return captchaId;
//    }
//
//    /**
//     * 验证用户输入的验证码是否正确
//     *
//     * @param userInputCaptcha 用户输入的验证码
//     * @param captchaId         验证码唯一ID
//     * @return 是否验证成功
//     */
//    public boolean validateCaptcha(String userInputCaptcha, String captchaId) {
//        try (Jedis jedis = jedisPool.getResource()) {
//            // 从Redis中取出正确的验证码
//            String correctCaptcha = jedis.get(captchaId);
//
//            if (correctCaptcha == null || !correctCaptcha.equalsIgnoreCase(userInputCaptcha)) {
//                return false;
//            }
//            // 清除已使用的验证码
//            jedis.del(captchaId);
//            return true;
//        }
//    }
//
//}
