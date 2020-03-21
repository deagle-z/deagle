
package com.zw.order.business.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
@TableName("Order_tbl")
public class OrderEntity {


    @ApiModelProperty("")
	private Integer id;
	

    @ApiModelProperty("")
	private String userId;
	

    @ApiModelProperty("")
	private String commodityCode;
	

    @ApiModelProperty("")
	private Integer count;
	

    @ApiModelProperty("")
	private Integer money;
	

}
