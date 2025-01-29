package com.xanadukeeper.glacierdiary.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 登出接口
 * @since 2025/1/24 9:19
 */
public class AuthLogoutHandler implements LogoutHandler {

    /**
     * 实现登出接口
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    }
}
