

package com.zw.base.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginAuthDto implements Serializable {
    private static final long serialVersionUID = -1137852221455042256L;
    private Long userId;
    private String loginName;
    private String userName;
    private Long groupId;
    private String orgCode;
    private String groupName;
    /**
     * 单位编码
     */
    private String companyCode;
    /**
     * 单位简称
     */
    private String companyName;

    /**
     * 岗位ID
     */
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
