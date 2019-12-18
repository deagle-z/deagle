package com.zw.base;

import javax.validation.constraints.NotNull;

/**
 * @author zw
 * @description 分页查询基础类
 * @date 2019/12/17
 */
public class QueryPage<T> {
    @NotNull
    public Integer currentPage;
    @NotNull
    public Integer size;
    public T detail;
    public String condition;
}
