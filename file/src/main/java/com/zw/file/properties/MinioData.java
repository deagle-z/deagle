package com.zw.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * minio文件服务配置文件
 *
 * @author zw562
 */
@Data
@Configuration
@ConfigurationProperties("boots.module.minio")
public class MinioData {
    private String url;

    private Integer port;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
