package com.glacier.glacierdiary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glacier.glacierdiary.dao.RegistrationCodeUsageMapper;
import com.glacier.glacierdiary.entity.RegistrationCodeUsage;
import com.glacier.glacierdiary.service.RegistrationCodeUsageService;
import org.springframework.stereotype.Service;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 注册码使用记录实现类
 * @since 2025/4/12 22:30
 */
@Service
public class RegistrationCodeUsageServiceImpl extends ServiceImpl<RegistrationCodeUsageMapper, RegistrationCodeUsage> implements RegistrationCodeUsageService {
}
