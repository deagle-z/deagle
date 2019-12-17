package com.zw.provider.major.mongo.entity;

import lombok.Data;

/**
  * @description TODO
  * @date 2019/12/17
  * @author zw
*/
@Data
public class PageModel {

    private Integer pageNumber = 1;
    private Integer pageSize = 10;

}
