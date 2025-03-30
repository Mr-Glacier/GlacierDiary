package com.glacier.glacierdiary.configuration.security;

import com.glacier.glacierdiary.common.result.ResultCode;
import com.glacier.glacierdiary.service.basic.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 登出接口
 * @since 2025/1/24 9:19
 */
@Component
public class AuthLogoutHandler implements LogoutHandler {

    @Autowired
    private RedisService redisService;

    /**
     * 实现登出接口
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            // 1. 从请求头中获取 Token
            String token = extractTokenFromRequest(request);
            if (token == null || token.isEmpty()) {

                response.sendError(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage());
                return;
            }
            // 2. 将 Token 加入 Redis 黑名单
            addToBlacklist(token);

            // 3. 清除用户的其他会话信息（如果有）
            clearUserSession(authentication);

            // 4. 返回登出成功响应
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\":\"Logout successful\"}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从请求头中提取 Token
     */
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // 去掉 "Bearer " 前缀
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 将 Token 加入 Redis 黑名单
     */
    private void addToBlacklist(String token) {
        // 使用 RedisService 设置 Token 黑名单，默认过期时间为 1 天
        // 一天过期时间
        redisService.set("blacklist:" + token, "revoked", 10000);
    }

    /**
     * 清除用户的其他会话信息
     */
    private void clearUserSession(Authentication authentication) {
        if (authentication != null) {
            String username = authentication.getName();
            // 使用 RedisService 删除用户的会话缓存
            // 查询用户缓存
            redisService.get("user_session:" + username);
            // 清除用户缓存
            redisService.set("user_session:" + username, null,1000);
        }
    }

}
