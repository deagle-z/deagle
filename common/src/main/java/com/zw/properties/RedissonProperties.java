package com.zw.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
  * Redisson属性
  * @date 2019/12/21
  * @author zw
*/
@Data
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {
    private int timeout = 3000;

    private String address;

    private String password;

    private int connectionPoolSize = 64;

    private int connectionMinimumIdleSize=10;

    private int slaveConnectionPoolSize = 250;

    private int masterConnectionPoolSize = 250;

    private String[] sentinelAddresses;

    private String masterName;
}
