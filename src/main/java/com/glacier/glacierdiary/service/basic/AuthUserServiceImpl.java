package com.glacier.glacierdiary.service.basic;

import com.glacier.glacierdiary.entity.SysRole;
import com.glacier.glacierdiary.entity.SysUser;
import com.glacier.glacierdiary.service.SysRoleService;
import com.glacier.glacierdiary.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 实现Security 的 UserDetailsService
 * @since 2025/1/20 3:11
 */
@Service
public class AuthUserServiceImpl implements UserDetailsService {

    private final SysUserService sysUserService;
    private final SysRoleService sysRolesService;

    public AuthUserServiceImpl(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRolesService = sysRoleService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<SysRole> rolesList = sysRolesService.getRoles();
        Map<Integer, String> rolesMap = rolesList.stream().collect(Collectors.toMap(SysRole::getId, SysRole::getRoleName));
        // 解析用户的角色列表
        List<GrantedAuthority> authorities = Arrays.stream(user.getRole().split(","))
                .filter(roleIdStr -> !roleIdStr.trim().isEmpty())
                .map(Integer::parseInt)
                .map(rolesMap::get)
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 构建 oauth 用户信息
        return User.withUsername(user.getUsername())
                // 因为密码注册的时候 就已经进行了加密，所以这里直接返回即可
                // {noop}表示不进行密码编码
                .password(user.getPassword())
                // 添加用户的角色列表
                .authorities(authorities)
                .build();
    }
}
