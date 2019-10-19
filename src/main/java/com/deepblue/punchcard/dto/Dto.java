package com.deepblue.punchcard.dto;

import lombok.Data;

/**
 * 传输对象
 */
@Data
public class Dto<T> {
    /**
     * 判断否正确的返回数据“true”返回正确 “false” 错误返回
     */
    private String success;
    /**
     * 返回的错误码 0表示没有错
     */
    private String errorCode;
    /**
     * 返回的错误信息
     */
    private String msg;
    /**
     * 返回的数据
     */
    private T data;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
