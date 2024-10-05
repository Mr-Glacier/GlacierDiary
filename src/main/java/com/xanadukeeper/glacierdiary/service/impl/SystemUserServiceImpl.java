package com.xanadukeeper.glacierdiary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xanadukeeper.glacierdiary.model.entity.SystemUser;
import com.xanadukeeper.glacierdiary.mapper.SystemUserMapper;
import com.xanadukeeper.glacierdiary.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2024-10-01
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 验证用户名密码是否正确
     */
    @Override
    public boolean checkUser(String userName, String userPassword) {
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        SystemUser user = systemUserMapper.selectOne(queryWrapper);
        if (user != null){
            if (user.getUserPassword().equals(userPassword)){
                return true;
            }else {
                throw new RuntimeException("密码错误");
            }
        }else {
            throw  new RuntimeException("用户不存在");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中加载用户信息
        SystemUser user = systemUserMapper.selectOne(new QueryWrapper<SystemUser>().eq("user_name", username));
        return User.withUsername(user.getUserName())
                // 因为密码注册的时候 就已经进行了加密，所以这里直接返回即可
                .password(user.getUserPassword()) // {noop}表示不进行密码编码
                .roles(user.getUserRole())
                .build();
    }

}
