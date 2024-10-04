package com.xanadukeeper.glacierdiary.common;

import java.io.Serializable;
/**
 * 结果信息类，用于封装API响应的结果信息。
 */
public class ResultInfo implements Serializable {

    // 状态码，用于表示操作的执行结果
    private Integer code;
    // 消息，用于提供操作结果的详细信息
    private String message;
    // 数据对象，用于承载操作结果的数据
    private Object result;

    // 总数，一般用于分页场景下表示总记录数
    private Integer total;

    /**
     * 无参构造器，创建一个默认的结果信息对象。
     */
    public ResultInfo() {
        super();
    }

    /**
     * 带状态码和消息的构造器。
     * @param status 包含状态码和消息的对象
     */
    public ResultInfo(Status status) {
        super();
        this.code = status.code;
        this.message = status.message;
    }

    /**
     * 设置结果数据并返回当前对象，用于链式调用。
     * @param result 结果数据
     * @return 当前ResultInfo对象
     */
    public ResultInfo result(Object result) {
        this.result = result;
        return this;
    }

    /**
     * 设置消息并返回当前对象，用于链式调用。
     * @param message 操作结果的消息
     * @return 当前ResultInfo对象
     */
    public ResultInfo message(String message) {
        this.message = message;
        return this;
    }

    /**
     * 设置总数并返回当前对象，用于链式调用。
     * @param total 总数
     * @return 当前ResultInfo对象
     */
    public ResultInfo total(Integer total) {
        this.total = total;
        return this;
    }

    /**
     * 带状态码和消息的构造器。
     * @param code 状态码
     * @param message 消息
     */
    public ResultInfo(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    /**
     * 带状态码和结果数据的构造器。
     * @param code 状态码
     * @param result 结果数据
     */
    public ResultInfo(Integer code, Object result) {
        super();
        this.code = code;
        this.result = result;
    }

    /**
     * 带状态码、消息和结果数据的构造器。
     * @param code 状态码
     * @param message 消息
     * @param result 结果数据
     */
    public ResultInfo(Integer code, String message, Object result) {
        super();
        this.code = code;
        this.message = message;
        this.result = result;
    }

}
