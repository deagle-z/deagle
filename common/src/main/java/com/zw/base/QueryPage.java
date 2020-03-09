package com.zw.base;

import lombok.Data;

/**
 * @author zw
 * @description 分页查询基础类
 * @date 2019/12/17
 */
@Data
public class QueryPage<T> {
    private Integer currentPage = 1;
    private Integer size = 10;
    private T detail;
    private String condition;
}
