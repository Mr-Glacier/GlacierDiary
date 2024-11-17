package com.xanadukeeper.glacierdiary.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xanadukeeper.glacierdiary.common.result.Result;
import com.xanadukeeper.glacierdiary.model.entity.SystemUser;
import com.xanadukeeper.glacierdiary.service.AuthUserService;
import com.xanadukeeper.glacierdiary.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 管理员登录控制器
 * @since 2024/10/1 20:24
 */
@RestController
@RequestMapping("/adminApi/auth")
public class amdinLonginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    private SystemUserService systemUserService;


    /**
     * @apiNote 系统用户登陆方法, 依赖Security ,自定义登录
     * 登录成功则返回管理端主页, 否则返回登录页
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam(name = "userName") String username,
                                     @RequestParam(name = "userPassword") String password) {
        try {
            // 创建认证令牌
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            // 执行认证
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 设置认证到安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 认证成功
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", true);
            responseMap.put("message", "登录成功");
            return responseMap;
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", false);
            responseMap.put("message", "用户名或密码错误");
            return responseMap;
        } catch (UsernameNotFoundException e){
            e.printStackTrace();
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", false);
            responseMap.put("message", "用户不存在");
            return responseMap;
        }
    }

    @PostMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {
        // 清除Spring Security的安全上下文
        SecurityContextHolder.clearContext();

        // 使用SecurityContextLogoutHandler清除会话
        new SecurityContextLogoutHandler().logout(request, response, null);

        // 删除JSESSIONID Cookie
        response.setHeader("Set-Cookie", "JSESSIONID=; Path=/; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT");

        // 重定向到登录页面或其他指定页面
        return new ModelAndView("redirect:/client/home");
    }

    /**
     * 注册需要注册密钥
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("userName") String userName,
                           @RequestParam("userPassword") String userPassword,
                           @RequestParam("registrationKey") String registrationKey) {
        // 检查注册密钥
        if (registrationKey.equals("Glacier2024GQiZ3ERxzy4cuWtGbJ7t35zzH")) {
            QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_name", userName);
            List<SystemUser> systemUserList = systemUserService.list(queryWrapper);

            if (null == systemUserList || systemUserList.isEmpty()) {
                SystemUser systemUser = new SystemUser();
                systemUser.setUserName(userName);
                systemUser.setUserPassword(passwordEncoder.encode(userPassword));
                systemUser.setUserRole("admin");
                systemUserService.save(systemUser);

                System.out.println("=====注册成功");
                return Result.success();
            } else {
                return Result.failed("User already exists");
            }
        } else {
            // 注册密钥不匹配，传递错误信息到前端
            return Result.failed("Internal server error");
        }
    }

    /**
     * 校验用户是否登录
     */
    @GetMapping("/checkAuth")
    public Map<String, Object> checkAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()&& !authentication.getName().equals("anonymousUser")) {
            // 用户已登录
            // 设置用户头像
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("isLoggedIn", true);
            responseMap.put("username", authentication.getName());
            return responseMap;
        } else {
            // 用户未登录
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("isLoggedIn", false);
            return responseMap;
        }
    }

}
