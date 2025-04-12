package com.glacier.glacierdiary.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glacier.glacierdiary.entity.RegistrationCodeUsage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 注册码使用记录Mapper
 * @since 2025/4/12 22:31
 */
@Mapper
public interface RegistrationCodeUsageMapper extends BaseMapper<RegistrationCodeUsage> {
}
