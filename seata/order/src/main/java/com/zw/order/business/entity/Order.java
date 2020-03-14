package com.zw.order.business.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_tbl")
public class Order {
    private Long id;

    private String userId;

    private String commodityCode;

    private String count;

    private String money;

}