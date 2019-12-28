package com.zw.response.handler;

import com.zw.exception.BusinessException;
import com.zw.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;


/**
 * 全局异常拦截
 *
 * @author zw
 * @date 2019/12/21
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus()
    @ResponseBody
    public R exception(Exception e) {
        if (e instanceof SQLException) {
            return new R().error(e.getMessage());
        } else if (e instanceof NullPointerException) {
            return new R().error(e.getMessage());
        } else if(e instanceof BusinessException){
            log.info("保存全局异常信息 msg={}，e={}", e.getMessage(), e);
            return new R().error(e.getMessage());
        } else{
            log.info("保存全局异常信息 msg={}，e={}", e.getMessage(), e);
            return new R().error("接口异常，请稍后操作！");
        }
    }
}
