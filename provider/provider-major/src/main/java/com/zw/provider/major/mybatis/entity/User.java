package com.zw.provider.major.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * user实体类
 *
 * @author zw
 * @date 2019/12/26
 */
@Data
@TableName("user")
public class User {
    @TableId
    private Long id;
    @TableField
    private String name;
    @TableField
    private String sex;
    @TableField
    private Integer age;
}
