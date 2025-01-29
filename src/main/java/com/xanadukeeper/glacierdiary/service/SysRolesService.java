package com.xanadukeeper.glacierdiary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xanadukeeper.glacierdiary.entity.po.SysRoles;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */
public interface SysRolesService extends IService<SysRoles> {

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    public List<SysRoles> getRoles();



}
