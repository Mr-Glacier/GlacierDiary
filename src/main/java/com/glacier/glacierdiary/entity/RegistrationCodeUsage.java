package com.glacier.glacierdiary.entity;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 注册码使用记录
 * @since 2025/4/12 22:15
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("registration_code_usages")
public class RegistrationCodeUsage {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("code_id")
    private RegistrationCode registrationCode;

    @TableField("user_id")
    private Long userId;

    @TableField("used_time")
    private LocalDateTime usedTime;

    @TableField("ip_address")
    private String ipAddress;

    @TableField("user_agent")
    private String userAgent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }

    public void setRegistrationCode(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(LocalDateTime usedTime) {
        this.usedTime = usedTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public String toString() {
        return "RegistrationCodeUsage{" +
                "id=" + id +
                ", registrationCode=" + registrationCode +
                ", userId=" + userId +
                ", usedTime=" + usedTime +
                ", ipAddress='" + ipAddress + '\'' +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }
}
