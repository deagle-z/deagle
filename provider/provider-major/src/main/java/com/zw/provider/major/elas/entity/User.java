package com.zw.provider.major.elas.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
  * es 测试
  * @date 2020/1/2
  * @author zw
*/
@Document(indexName = "userindex",type = "user")
@Data
public class User {

    private Long id;

    private String name;

    private Integer age;

    private Integer currentPage;

    private Integer size;
}
