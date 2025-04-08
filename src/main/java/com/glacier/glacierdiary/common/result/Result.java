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
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 静态方法：失败返回（无数据）
     */
    public static <T> Result<T> failed() {
        return result(ResultCode.INTERNAL_SERVER_ERROR.getCode(), ResultCode.INTERNAL_SERVER_ERROR.getMessage(), null);
    }

    /**
     * 错误返回,并自定义消息
     */
    public static <T> Result<T> failed(String msg) {
        return result(ResultCode.INTERNAL_SERVER_ERROR.getCode(), msg, null);
    }

    /**
     * 构建结果对象,使用相应码枚举
     */
    private static <T> Result<T> result(Integer code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setData(data);
        result.setMessage(msg);
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