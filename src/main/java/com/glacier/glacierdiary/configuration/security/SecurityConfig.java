package com.glacier.glacierdiary.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * SpringSecurity的配置
 *
 * @author Mr-Glacier
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 禁用 CSRF（因为我们使用 JWT 进行身份验证）
        httpSecurity.csrf().disable()
                // 设置为无状态会话管理（基于 Token，不需要 Session）
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 配置请求授权规则
                .authorizeRequests()
                // 允许对静态资源的无授权访问
                .antMatchers(HttpMethod.GET,
                        "/", "/*.html", "/images/**", "/images/icon/**", "/css/**", "/js/**", "/fonts/**", "/swagger-resources/**", "/v2/api-docs/**", "/webjars/**"
                ).permitAll()
                // OAuth 认证模块无需权限校验
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/api/system/**").permitAll()
                // 验证码模块无需权限校验
                .antMatchers("/api/captcha/**").permitAll()
                // 针对 /api/admin/** 请求需要 ADMIN 权限
                .antMatchers("/api/admin/**").hasAuthority("ADMIN")
                // 跨域预检请求（OPTIONS 方法）无需权限校验
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 测试时可以放开所有路径（注释掉以启用正式环境配置）
                .antMatchers("/**").permitAll()
                // 除以上路径外的所有请求都需要鉴权认证
                .anyRequest().authenticated();

        // 禁用缓存
        httpSecurity.headers().cacheControl();

        // 添加自定义 JWT 认证过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 自定义异常处理：未授权和未登录的返回结果
        httpSecurity.exceptionHandling()
                // 处理未授权访问
                .accessDeniedHandler(restfulAccessDeniedHandler)
                // 处理未登录访问
                .authenticationEntryPoint(restAuthenticationEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }


    /**
     * 配置密码加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 提供 AuthenticationManager Bean，以便在自定义登录逻辑（如 JWT 登录）中使用。
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
