package com.zw.util.base;

/**
 * @author zw
 * @description 分页查询基础类
 * @date 2019/12/17
 */
public class QueryPage<T> {
    public Integer currentPage;
    public Integer size;
    public String sort;
    public T condition;
}
