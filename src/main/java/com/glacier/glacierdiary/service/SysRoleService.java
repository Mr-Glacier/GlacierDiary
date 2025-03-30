package com.glacier.glacierdiary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glacier.glacierdiary.entity.SysRole;

import java.util.List;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 系统角色service接口
 * @since 2025/3/30 23:15
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 获取系统所有角色
     * @return 系统所有角色
     */
    List<SysRole> getRoles();
}
