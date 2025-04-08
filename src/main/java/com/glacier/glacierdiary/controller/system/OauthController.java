package com.glacier.glacierdiary.controller.system;

import com.glacier.glacierdiary.common.result.Result;
import com.glacier.glacierdiary.configuration.security.JwtTokenUtil;
import com.glacier.glacierdiary.entity.SysUser;
import com.glacier.glacierdiary.service.SysUserService;
import com.glacier.glacierdiary.service.basic.AuthUserServiceImpl;
import com.glacier.glacierdiary.service.basic.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 认证授权控制类
 * @since 2025/1/20 1:29
 */
@RestController
@Api(tags = "Oauth 认证模块")
@RequestMapping("/oauth")
public class OauthController {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthUserServiceImpl authUserService;

    private final RedisService redisService;

    public OauthController(SysUserService sysUserService, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil, AuthUserServiceImpl authUserService, RedisService redisService) {
        this.sysUserService = sysUserService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authUserService = authUserService;
        this.redisService = redisService;
    }

    /**
     * @apiNote 登录接口, 接收用户名密码, 返回token
     */
    @ApiOperation(value = "用户登录", notes = "此接口用于通过用户名和密码进行用户登录。", response = Result.class, httpMethod = "POST")
    @PostMapping("/login")
    public Result<Object> login(@RequestParam(name = "userName") String userName,
                                @RequestParam(name = "userPassword") String userPassword) {
        try {
            // 根据账号查询用户
            SysUser currentUser = sysUserService.getUserByUserName(userName);
            if (currentUser == null) {
                return Result.failed("用户不存在");
            }
            // 调用方法加载用户
            UserDetails userDetails = authUserService.loadUserByUsername(userName);
            // 密码校验
            if (!passwordEncoder.matches(userPassword, userDetails.getPassword())) {
                return Result.failed("用户名或密码错误");
            }
            // 校验用户是否被封禁
            if (currentUser.getStatus() == 0) {
                return Result.failed("用户已被封禁");
            }
            if (currentUser.getStatus() == 2) {
                return Result.failed("用户已被删除,请联系管理员!");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken(userDetails);
            return Result.success(token);
        } catch (org.springframework.security.core.AuthenticationException e) {
            // 认证失败处理
            return Result.failed("账户或密码错误");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("系统错误，请稍后再试");
        }
    }


    /**
     * 注册用户,仅允许注册普通用户账号
     *
     * @param userName 用户名 userPassword 用户密码 registrationKey 注册码
     */
    @ApiOperation(value = "用户注册", notes = "通过系统服务只能注册普通用户")
    @PostMapping("/register")
    @ResponseBody
    public Result<Object> register(@RequestParam("userName") String userName,
                                   @RequestParam("userPassword") String userPassword,
                                   @RequestParam("registrationKey") String registrationKey) {
        try {
            // 对三个参数进行校验
            if (userName == null || userPassword == null) {
                return Result.failed("注册相关信息错误");
            }
            // 判断用户是否存在
            SysUser sysUser = sysUserService.getUserByUserName(userName);
            if (sysUser != null) {
                return Result.failed("该账户已存在");
            }
            // 用户不存在进行注册
            SysUser registerUser = new SysUser();
            registerUser.setUsername(userName);
            registerUser.setPassword(passwordEncoder.encode(userPassword));
            registerUser.setRole("2");
            if (sysUserService.registerUser(registerUser)) {
                return Result.success("注册成功");
            } else {
                return Result.failed("注册失败");
            }
        } catch (Exception e) {
            return Result.failed("注册失败" + e.getMessage());
        }
    }

    /**
     * 刷新token
     */
    @ApiOperation("刷新认证")
    @GetMapping("/refreshToken")
    public Result<String> refreshToken(@RequestParam("token") String token) {
        if (jwtTokenUtil.canRefresh(token)) {
            return Result.success(jwtTokenUtil.refreshToken(token));
        } else {
            return Result.failed("token已失效");
        }
    }

    @ApiOperation("退出认证")
    @GetMapping("/logout")
    public Result<String> logout(@RequestParam("token") String token) {
        // 提取 JWT 的唯一标识符 (JTI)
        String jti = jwtTokenUtil.getJti(token);
        Date expiration = jwtTokenUtil.getExpiredDateFromToken(token);
        long currentTimeMillis = System.currentTimeMillis();
        long expirationTimeMillis = expiration.getTime();
        long expirationDuration = expirationTimeMillis - currentTimeMillis;
        redisService.set("logout:"+jti, "revoked", expirationDuration);
        if (jwtTokenUtil.canRefresh(token)) {
            return Result.success("退出成功");
        } else {
            return Result.failed("退出失败");
        }
    }
}
