package com.glacier.glacierdiary.common.result;

import java.io.Serializable;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 封装结果类
 * @since 2025/3/27 20:53
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据返回
     */
    private T data;

    /**
     * 私有构造方法，防止直接实例化
     */
    private Result() {
    }

    /**
     * 静态方法：成功返回（无数据）
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 静态方法：成功返回（带数据）
     */
    public static <T> Result<T> success(T data) {
        return build(ResultCode.SUCCESS, data);
    }

    /**
     * 静态方法：成功返回（自定义数据）
     */
    public static <T> Result<T> success(ResultCode resultCode, T data) {
        return build(resultCode, data);
    }

    /**
     * 静态方法：失败返回（无数据）
     */
    public static <T> Result<T> failed(ResultCode resultCode) {
        return build(resultCode, null);
    }

    /**
     * 静态方法：失败返回（自定义数据）
     */
    public static <T> Result<T> failed(ResultCode resultCode, T data) {
        return build(resultCode, data);
    }


    /**
     * 构建结果对象,使用相应码枚举
     */
    private static <T> Result<T> build(ResultCode resultCode, T data) {
        Result<T> result = new Result<>();
        result.setCode(resultCode.getCode());
        result.setMessage(resultCode.getMessage());
        result.setData(data);
        return result;
    }


    /**
     * Getter 和 Setter 方法
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 重写 toString 方法，方便调试
     */
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}