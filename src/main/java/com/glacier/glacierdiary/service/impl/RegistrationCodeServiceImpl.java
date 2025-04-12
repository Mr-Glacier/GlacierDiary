package com.glacier.glacierdiary.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.glacier.glacierdiary.dao.RegistrationCodeMapper;
import com.glacier.glacierdiary.entity.RegistrationCode;
import com.glacier.glacierdiary.service.RegistrationCodeService;
import org.springframework.stereotype.Service;

import javax.imageio.spi.RegisterableService;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 注册码服务实现类
 * @since 2025/4/12 22:26
 */
@Service
public class RegistrationCodeServiceImpl extends ServiceImpl<RegistrationCodeMapper, RegistrationCode> implements RegistrationCodeService {
}
