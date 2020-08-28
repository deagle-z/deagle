package com.zw.base.entity;

import lombok.Data;

import java.util.Date;

/**
  * 基础实体类
  * @date 2020/3/3
  * @author zw
*/
@Data
public class BaseEntity {
    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

}
