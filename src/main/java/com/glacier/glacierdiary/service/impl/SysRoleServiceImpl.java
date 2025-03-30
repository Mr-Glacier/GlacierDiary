package com.glacier.glacierdiary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glacier.glacierdiary.dao.SysRoleMapper;
import com.glacier.glacierdiary.dao.SysUserMapper;
import com.glacier.glacierdiary.entity.SysRole;
import com.glacier.glacierdiary.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 系统角色实现类
 * @since 2025/3/30 23:16
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    public List<SysRole> getRoles() {
        // 查询所有角色
        return sysRoleMapper.selectList(null);
    }
}
