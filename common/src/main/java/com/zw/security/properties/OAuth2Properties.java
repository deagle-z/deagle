/*
 * COPYRIGHT China Mobile (SuZhou) Software Technology Co.,Ltd. 2019
 *
 * The copyright to the computer program(s) herein is the property of
 * CMSS Co.,Ltd. The programs may be used and/or copied only with written
 * permission from CMSS Co.,Ltd. or in accordance with the terms and conditions
 * stipulated in the agreement/contract under which the program(s) have been
 * supplied.
 */

package com.chinamobile.cmss.cpms.common.security.properties;

import lombok.Data;

/**
 * The class O auth 2 properties.
 */
@Data
public class OAuth2Properties {

    /**
     * 使用jwt时为token签名的秘钥
     */
    private String jwtSigningKey = "cpms-a2bab0bcada1436aa7bd9f4748da2425";

    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

}
