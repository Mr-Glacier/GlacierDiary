package com.glacier.glacierdiary.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glacier.glacierdiary.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote sys_user 系统用户 mapper
 * @since 2025/3/30 22:54
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}