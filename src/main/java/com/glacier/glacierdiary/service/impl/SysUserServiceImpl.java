package com.glacier.glacierdiary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glacier.glacierdiary.dao.SysUserMapper;
import com.glacier.glacierdiary.entity.SysUser;
import com.glacier.glacierdiary.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * sys_user服务实现类
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    public SysUserServiceImpl(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 根据账号获取用户信息
     *
     * @param userName 账号
     * @return 用户信息
     */
    @Override
    public SysUser getUserByUserName(String userName) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userName);
        return sysUserMapper.selectOne(queryWrapper);
    }

    /**
     * 注册普通用户
     *
     * @param sysUser 用户信息实体
     * @return boolean
     */
    @Override
    public boolean registerUser(SysUser sysUser) {
        try {
            sysUserMapper.insert(sysUser);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("注册失败" + e.getMessage());
        }
    }

    /**
     * 删除用户
     *
     * @return boolean 是否删除成功
     */
    @Override
    public boolean deleteUser(String account) {
        try {
            UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("account", account);
            updateWrapper.set("status", 2);
            int rowsAffected = sysUserMapper.update(null, updateWrapper);
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException("删除失败" + e.getMessage());
        }
    }
}
