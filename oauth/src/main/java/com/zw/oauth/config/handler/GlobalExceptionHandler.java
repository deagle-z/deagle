package com.zw.oauth.config.handler;

import com.zw.exception.BusinessException;
import com.zw.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


/**
 * 全局异常拦截
 *
 * @author zw
 * @date 2019/12/21
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    public R<?> exception(Exception e) {
        log.error("保存全局异常拦截 msg={}", e.getMessage(), e);
        if (e instanceof SQLException) {
            return R.error("服务器异常，请稍后在进行操作！");
        } else if (e instanceof NullPointerException) {
            return R.error(e.getMessage());
        } else if(e instanceof BusinessException){
            return R.error(e.getMessage());
        } else{
            return R.error("接口异常，请稍后操作！");
        }
    }


}
