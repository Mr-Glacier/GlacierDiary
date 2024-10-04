package com.xanadukeeper.glacierdiary.config;


import com.xanadukeeper.glacierdiary.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote Security配置
 * @since 2024/10/2 2:00
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private AuthUserService authUserService;



    /**
     * 配置密码加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authUserService); // 设置 UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // 设置 PasswordEncoder
        return authProvider;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    /**
     * 主要功能为过滤拦截
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 如果不需要CSRF保护，可以禁用
                .authorizeRequests(authorize -> authorize
                        .antMatchers("/static/**").permitAll()
                        .antMatchers("/api/captcha/**").permitAll() // 验证码
                        .antMatchers("/adminWeb/adminLogin").permitAll() // 登录页面
                        .antMatchers("/adminWeb/adminRegister").permitAll() // 注册页面
                        .antMatchers("/adminApi/auth/**").permitAll() // 授权相关接口
                        .antMatchers("/adminApi/**").hasRole("admin") // 管理相关接口
                        .antMatchers("/client/**").permitAll() // 客户端相关接口
                        .antMatchers("/clientApi/**").permitAll()
                        .antMatchers("/").permitAll() // 首页
                        .anyRequest().authenticated()
                )
//                // 默认表单登录
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/")
//                        .permitAll()
//                )
                .formLogin(AbstractHttpConfigurer::disable) // 禁用默认的表单登录
//                .logout(LogoutConfigurer::permitAll) //登出操作
                .logout(LogoutConfigurer::disable) //禁用登出操作
                .sessionManagement(session -> session
                        .maximumSessions(1) // 每个用户只允许一个会话
                        .maxSessionsPreventsLogin(false) // 当达到最大会话数时，不阻止新的登录
                        .expiredUrl("/login?expired") // 会话过期后的重定向URL
                );

        return http.build();
    }




}