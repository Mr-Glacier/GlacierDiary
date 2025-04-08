package com.glacier.glacierdiary.configuration.security;

import com.glacier.glacierdiary.service.basic.AuthUserServiceImpl;
import com.glacier.glacierdiary.service.basic.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 拦截器
 * @since 2025/1/20 17:52
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private JwtTokenUtil jwtTokenUtil;

    private AuthUserServiceImpl authUserService;

    private RedisService redisService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, AuthUserServiceImpl authUserService, RedisService redisService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authUserService = authUserService;
        this.redisService = redisService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";

        if (token != null && token.startsWith(tokenHead)) {
            String authToken = token.substring(tokenHead.length());
            String username = jwtTokenUtil.getUserNameFromToken(authToken);

            // 验证Token是否在黑名单
            String jti = jwtTokenUtil.getJti(authToken);
            if (redisService.get("logout:" + jti) != null) {
                LOGGER.info("Token is in blacklist, user:{}", username);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            LOGGER.info("checking username:{}", username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = authUserService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    LOGGER.info("authenticated user:{}", username);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}