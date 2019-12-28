package com.zw.exception;

import lombok.Data;

/**
  * 业务异常类
  * @date 2019/12/26
  * @author zw
*/
@Data
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int bizCode = 1000;
    private String message;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BusinessException(String message) {
        this.message = message;
    }
}
