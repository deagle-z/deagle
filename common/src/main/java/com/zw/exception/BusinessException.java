package com.zw.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
  * 业务异常类
  * @date 2019/12/26
  * @author zw
  * //callSuper : false 不使用父类的参数加子类的参数来生成hashCode
 */

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final int BIZ_CODE = 1000;
    private String message;
    private String code ;
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BusinessException(String message) {
        this.message = message;
    }

    public static BusinessException userInfoInvalidException(){
        return new BusinessException("401", "用户信息失效");
    }

}
