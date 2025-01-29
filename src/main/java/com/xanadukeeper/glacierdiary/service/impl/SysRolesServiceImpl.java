package com.xanadukeeper.glacierdiary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xanadukeeper.glacierdiary.entity.po.SysRoles;
import com.xanadukeeper.glacierdiary.mapper.SysRolesMapper;
import com.xanadukeeper.glacierdiary.service.SysRolesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2025-01-20
 */
@Service
public class SysRolesServiceImpl extends ServiceImpl<SysRolesMapper, SysRoles> implements SysRolesService {

    public final SysRolesMapper sysRolesMapper;

    public SysRolesServiceImpl(SysRolesMapper sysRolesMapper) {
        this.sysRolesMapper = sysRolesMapper;
    }

    @Override
    public List<SysRoles> getRoles() {
        return sysRolesMapper.selectList(null);
    }
}
