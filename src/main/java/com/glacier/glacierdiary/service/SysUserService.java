package com.glacier.glacierdiary.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.glacier.glacierdiary.entity.SysUser;

/**
 * sys_user 服务类 service 接口
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据账号查询用户信息
     *
     * @param userName 账号
     * @return 用户信息
     */
    SysUser getUserByUserName(String userName);

    /**
     * 注册新用户(仅允许注册普通用户)
     *
     * @param sysUser 用户信息实体
     * @return true:注册成功 false:注册失败
     */
    boolean registerUser(SysUser sysUser);

    /**
     * 删除用户 (更改用户状态为删除 status = 2 )
     *
     * @param account 用户账号
     * @return true:删除成功 false:删除失败
     */
    boolean deleteUser(String account);

}


