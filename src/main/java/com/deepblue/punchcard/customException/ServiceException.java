package com.deepblue.punchcard.customException;

import java.io.Serializable;

/**
 * 自定义异常
 */
public class ServiceException extends  RuntimeException implements Serializable {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
