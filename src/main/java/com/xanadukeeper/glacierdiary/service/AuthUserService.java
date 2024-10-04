package com.xanadukeeper.glacierdiary.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xanadukeeper.glacierdiary.mapper.SystemUserMapper;
import com.xanadukeeper.glacierdiary.model.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 实现Security的UserDetailsService
 * @since 2024/10/2 13:35
 */
@Service
public class AuthUserService implements UserDetailsService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中加载用户信息
        SystemUser user = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("user_name", username));
        if (user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return User.withUsername(user.getUserName())
                // 因为密码注册的时候 就已经进行了加密，所以这里直接返回即可
                .password(user.getUserPassword()) // {noop}表示不进行密码编码
                .roles(user.getUserRole())
                .build();
    }
}
