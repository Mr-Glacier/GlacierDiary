package com.xanadukeeper.glacierdiary.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xanadukeeper.glacierdiary.common.result.Result;
import com.xanadukeeper.glacierdiary.entity.po.SysRoles;
import com.xanadukeeper.glacierdiary.service.SysRolesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 系统角色控制类
 * @since 2025/1/23 18:11
 */
@RestController
@Api(tags = "角色管理模块")
@RequestMapping("/api/admin/sysRole")
public class AdminSysRolesController {

    private final SysRolesService sysRolesService;

    public AdminSysRolesController(SysRolesService sysRolesService) {
        this.sysRolesService = sysRolesService;
    }


    /**
     * 增加角色
     */
    @ApiOperation(value = "删除角色", notes = "删除角色")
    @PostMapping("/delete")
    public Result<Object> deleteSysRolesController(@RequestParam("roleName") String roleName) {
        System.out.println("删除角色");
        try {
            QueryWrapper<SysRoles> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_name", roleName);
            SysRoles sysRoles = sysRolesService.getOne(queryWrapper);
            if (sysRolesService.getOne(queryWrapper) == null) {
                return Result.failed("无该角色");
            }
            if ("ADMIN".equals(sysRolesService.getOne(queryWrapper).getRoleName())) {
                return Result.failed("管理员角色不能删除");
            }
            if (sysRolesService.removeById(sysRoles)) {
                return Result.success("删除成功");
            } else {
                return Result.failed("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("删除失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @PostMapping("/update")
    public Result<Object> updateSysRolesController(@RequestParam("roleName") String roleName) {
        try {
            QueryWrapper<SysRoles> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_name", roleName);
            if (sysRolesService.getOne(queryWrapper) != null) {
                return Result.failed("该角色已存在");
            }

            SysRoles sysRoles = sysRolesService.getOne(queryWrapper);
            sysRoles.setRoleName(roleName);
            if (sysRolesService.updateById(sysRoles)) {
                return Result.success("修改成功");
            } else {
                return Result.failed("修改失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("修改失败" + e.getMessage());
        }
    }

    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    @PostMapping("/selectList")
    public Result<Object> selectListSysRolesController() {
        return Result.success(sysRolesService.getRoles());
    }

    @ApiOperation(value = "增加角色", notes = "增加角色")
    @PostMapping("/add")
    public Result<Object> addSysRolesController(@RequestParam("roleName") String roleName) {
        try {
            QueryWrapper<SysRoles> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_name", roleName);
            if (sysRolesService.getOne(queryWrapper) != null) {
                return Result.failed("该角色已存在");
            }
            SysRoles sysRoles = new SysRoles();
            sysRoles.setRoleName(roleName);
            if (sysRolesService.save(sysRoles)) {
                return Result.success("增加成功");
            } else {
                return Result.failed("增加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failed("增加失败" + e.getMessage());
        }
    }

}
