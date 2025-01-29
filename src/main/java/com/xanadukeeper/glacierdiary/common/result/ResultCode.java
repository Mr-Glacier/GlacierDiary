package com.xanadukeeper.glacierdiary.common.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Mr-Glacier
 * @version 1.0
 * <p>
 * 鉴权模块错误码: A400-A500
 * 其他组件错误码: A500-A600
 * 系统执行错误: A600-A700
 * </p>
 * @apiNote 规范系统返回码
 * @since 2025/01/20
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {
    // 错误码分为三个大类 A400-A500 A500-A600 A600-A700
    SUCCESS("200", "成功"),
    // 授权登录模块错误码
    USER_NOT_EXIST("A401", "用户不存在"),
    USER_PASSWORD_ERROR("A402", "用户密码错误"),
    VERIFICATION_CODE_ERROR("A410", "验证码错误"),
    SYSTEM_EXECUTION_ERROR("500", "系统执行错误"),
    ;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }


    public static ResultCode getValue(String code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        // 默认系统执行错误
        return SYSTEM_EXECUTION_ERROR;
    }
}
