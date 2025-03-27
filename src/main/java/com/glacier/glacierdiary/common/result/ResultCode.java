package com.glacier.glacierdiary.common.result;

/**
 * @author Mr-Glacier
 * @version 1.0
 * @apiNote 枚举结果响应码
 * @since 2025/3/27 20:53
 */
public enum ResultCode {
    // 操作成功
    SUCCESS(200, "操作成功"),
    // 操作失败
    BAD_REQUEST(400, "请求参数错误"),
    // 未授权
    UNAUTHORIZED(401, "未授权访问"),
    // 权限不足
    FORBIDDEN(403, "禁止访问"),
    // 资源不存在
    NOT_FOUND(404, "资源未找到"),
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    ;
    private final int code;
    private final String message;

    /**
     * 构造方法
     */
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
