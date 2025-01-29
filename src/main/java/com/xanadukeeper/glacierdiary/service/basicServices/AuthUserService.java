package com.xanadukeeper.glacierdiary.service.basicServices;

import com.xanadukeeper.glacierdiary.entity.po.SysRoles;
import com.xanadukeeper.glacierdiary.entity.po.SysUser;
import com.xanadukeeper.glacierdiary.service.SysRolesService;
import com.xanadukeeper.glacierdiary.service.SysUserService;
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
public class AuthUserService implements UserDetailsService {

    private final SysUserService sysUserService;

    private final SysRolesService sysRolesService;

    public AuthUserService(SysUserService sysUserService, SysRolesService sysRolesService) {
        this.sysUserService = sysUserService;
        this.sysRolesService = sysRolesService;
    }


    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        SysUser user = sysUserService.getUserByAccount(account);
        if (null == user) {
            throw new UsernameNotFoundException("用户不存在");
        }

        List<SysRoles> rolesList = sysRolesService.getRoles();
        Map<Integer, String> rolesMap = rolesList.stream().collect(Collectors.toMap(SysRoles::getId, SysRoles::getRoleName));
        // 解析用户的角色列表
        List<GrantedAuthority> authorities = Arrays.stream(user.getRoleList().split(","))
                .filter(roleIdStr -> !roleIdStr.trim().isEmpty())
                .map(Integer::parseInt)
                .map(rolesMap::get)
                .filter(Objects::nonNull)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());


        return User.withUsername(user.getUserName())
                // 因为密码注册的时候 就已经进行了加密，所以这里直接返回即可
                // {noop}表示不进行密码编码
                .password(user.getPassWord())
                // 添加用户的角色列表
                .authorities(authorities)
                .build();
    }
}
