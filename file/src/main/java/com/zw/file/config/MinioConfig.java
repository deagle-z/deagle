package com.zw.file.config;

import com.zw.file.properties.MinioData;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * minio配置类
 *
 * @author zw562
 */
@Slf4j
@Configuration
public class MinioConfig {

    @Resource
    private MinioData minioData;

    @Bean
    public MinioClient minioClient(){
        try {
            return new MinioClient(minioData.getUrl(),minioData.getPort(), minioData.getAccessKey(), minioData.getSecretKey(),false);
        } catch (Exception e) {
            log.info("文件服务初始化异常:{}", e.fillInStackTrace().toString());
            throw new RuntimeException(e);
        }
    }

}
