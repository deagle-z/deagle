package com.zw.provider.major.mongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


/**
 * @author zw
 * @description mongodb 中process_instance
 * @date 2019/12/17
 */

@Document(collation = "process_instance")
@Data
public class ProcessInstance {
    @Id
    private String instanceId;
    private String processId;
    private String name;
    private String applicantId;
    private String applicantName;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    private String isDelete;
}
