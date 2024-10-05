package com.xanadukeeper.glacierdiary.service;

import com.xanadukeeper.glacierdiary.model.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mr-Glacier
 * @since 2024-10-01
 */
public interface SystemUserService extends IService<SystemUser> {
    boolean checkUser(String userName, String userPassword);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
