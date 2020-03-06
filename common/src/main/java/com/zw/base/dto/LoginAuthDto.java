

package com.zw.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel(value = "登录人信息")
public class LoginAuthDto implements Serializable {
    private static final long serialVersionUID = -1137852221455042256L;
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    @ApiModelProperty(value = "登录名")
    private String loginName;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "所属组织ID")
    private Long groupId;
    @ApiModelProperty(value = "所属组织code")
    private String orgCode;
    @ApiModelProperty(value = "所属组织名称")
    private String groupName;
    /**
     * 单位编码
     */
    @ApiModelProperty(value = "单位编码")
    private String companyCode;
    /**
     * 单位简称
     */
    @ApiModelProperty(value = "单位简称")
    private String companyName;

    /**
     * 岗位ID
     */
    @ApiModelProperty(value = "岗位ID")
    private Long positionId;


    public LoginAuthDto() {
    }

    public LoginAuthDto(final Long userId, final String loginName, final String userName) {
        this.userId = userId;
        this.loginName = loginName;
        this.userName = userName;
    }

    public LoginAuthDto(final Long userId, final String loginName, final String userName, final Long groupId,
                        final String orgCode, final String groupName, final String companyCode, final String companyName,
                        final Long positionId) {
        this.userId = userId;
        this.loginName = loginName;
        this.userName = userName;
        this.groupId = groupId;
        this.orgCode = orgCode;
        this.groupName = groupName;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.positionId = positionId;
    }
}
