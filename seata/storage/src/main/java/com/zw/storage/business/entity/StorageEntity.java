
package com.zw.storage.business.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel
@TableName("Storage_tbl")
public class StorageEntity {


    @ApiModelProperty("")
	private Integer id;
	

    @ApiModelProperty("")
	private String commodityCode;
	

    @ApiModelProperty("")
	private Integer count;
	

}
