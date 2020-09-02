package com.zw.file.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zw
 * @since 2020-09-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_file")
@ApiModel(value="SysFileEntity对象", description="")
@NoArgsConstructor
public class SysFileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type =IdType.AUTO)
    private Integer fileId;

    private String fileName;

    private String fileUrl;

    private LocalDateTime createTime;

    private Long createBy;

    private LocalDateTime updateTime;

    private Long updateBy;

    private String deleteFlag;

    public SysFileEntity(String fileName, String fileUrl) {
        this.fileName = fileName;
        this.fileUrl = fileUrl;
    }
}
