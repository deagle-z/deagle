package com.zw.response;

import java.io.Serializable;

/**
 * 统一结果返回类
 *
 * @author zw
 * @date 2019/12/21
 */
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int NO_LOGIN = 401;

    public static final int SUCCESS = 200;

    public static final int FAIL = 500;

    public static final int NO_PERMISSION = 403;

    private static final String MSG = "success";

    public static final String FAIL_MSG = "error";
    private String msg = "success";


    private int code = SUCCESS;

    private T data;

    public R() {
        super();
    }

    public R(T data) {
        super();
        this.data = data;
    }

    public R(T data, String msg) {
        super();
        this.data = data;
        this.msg = msg;
    }


    public R(String msg, int code) {
        super();
        this.code = code;
        this.msg = msg;
    }
    public R(int code, T data) {
        super();
        this.data = data;
        this.code = code;
    }


    public R(Throwable e) {
        super();
        this.msg = e.getMessage();
        this.code = FAIL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static R<Boolean> rest(boolean result) {
        R<Boolean> r = new R<Boolean>();
        if (!result) {
            r.setCode(R.FAIL);
            r.setData(false);
        }
        return r;
    }

    public static R success() {
        return new R(MSG, SUCCESS);
    }

    public static R success(String msg){
        return new R(msg, SUCCESS);
    }

    public  R success(T data){
        return new R(SUCCESS,data);
    }

    public R error() {
        return new R(msg, FAIL);
    }

    public static R error(String msg) {
        return new R(msg, FAIL);
    }

    public static R error(Integer code){
        return new R("error",code);
    }
}
