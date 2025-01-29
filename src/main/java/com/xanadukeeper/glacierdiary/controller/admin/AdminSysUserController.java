package com.xanadukeeper.glacierdiary.controller.admin;

import com.xanadukeeper.glacierdiary.common.result.Result;
import com.xanadukeeper.glacierdiary.common.result.ResultCode;
import com.xanadukeeper.glacierdiary.entity.po.SysUser;
import com.xanadukeeper.glacierdiary.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 管理员用户控制类
 * @since 2025/1/21 19:20
 */
@RestController
@Api(tags = "用户管理模块")
@RequestMapping("/api/admin/sysUser")
public class AdminSysUserController {
    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;

    public AdminSysUserController(SysUserService sysUserService, PasswordEncoder passwordEncoder) {
        this.sysUserService = sysUserService;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * 增加系统用户,可以注册的时候就进行权限区分
     *
     * @since 2025/1/21 19:20
     */
    @ApiOperation(value = "新增用户", notes = "新增用户,同时可以增加管理员用户,AMDIN 1 , USER 2")
    @PostMapping("/add")
    public Result<Object> addSysUserController(@RequestParam("account") String account, @RequestParam("userName") String userName,
                                               @RequestParam("userPassword") String userPassword, @RequestParam("roleList") String roleList) {
        try {
            // 参数进行 校验
            if (account == null || userName == null || userPassword == null || roleList == null) {
                return Result.failed("注册相关信息错误");
            }
            if (sysUserService.getUserByAccount(account) != null) {
                return Result.failed("该账户已存在");
            }
            SysUser sysUser = new SysUser();
            sysUser.setAccount(account);
            sysUser.setUserName(userName);
            sysUser.setPassWord(passwordEncoder.encode(userPassword));
            sysUser.setRoleList(roleList);
            if (sysUserService.save(sysUser)) {
                return Result.success("注册成功");
            } else {
                return Result.failed("注册失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed(ResultCode.SYSTEM_EXECUTION_ERROR, "注册失败" + e.getMessage());
        }
    }


    @ApiOperation(value = "删除用户", notes = "删除用户")
    @PostMapping("/delete")
    public Result<Object> deleteSysUserController(@RequestParam("account") String account) {
        try {
            SysUser sysUser = sysUserService.getUserByAccount(account);
            if (sysUser == null) {
                return Result.failed("该账户不存在");
            }
            if (sysUserService.deleteUser(account)) {
                return Result.success("删除成功");
            } else {
                return Result.failed("删除失败");
            }
        } catch (Exception e) {
            return Result.failed("删除失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "修改用户", notes = "修改用户名称、权限、密码、等数据")
    @PostMapping("/update")
    public Result<Object> updateSysUserController(@RequestParam("account") String account, @RequestParam("userName") String userName,
                                                  @RequestParam("userPassword") String userPassword, @RequestParam("roleList") String roleList,
                                                  @RequestParam("userStatus") String userStatus) {
        try {
            SysUser sysUser = sysUserService.getUserByAccount(account);
            if (sysUser == null) {
                return Result.failed("该账户不存在");
            }
            sysUser.setUserName(userName);
            sysUser.setPassWord(passwordEncoder.encode(userPassword));
            sysUser.setRoleList(roleList);
            sysUser.setStatus(Integer.parseInt(userStatus));
            if (sysUserService.updateById(sysUser)) {
                return Result.success("修改成功");
            } else {
                return Result.failed("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("修改失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    @GetMapping("/selectList")
    public Result<Object> selectListSysUserController() {
        List<SysUser> sysUserList = sysUserService.list();
        sysUserList.forEach(sysUser -> {
            sysUser.setPassWord("******");
        });
        return Result.success(sysUserList);
    }

}
