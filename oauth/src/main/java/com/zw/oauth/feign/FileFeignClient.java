package com.zw.oauth.feign;


import com.alibaba.fastjson.JSONObject;
import com.zw.response.R;
import feign.Request;
import feign.Response;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.*;

@FeignClient(value = "/file", configuration = { FileFeignClient.MultipartSupportConfig.class})
public interface FileFeignClient {

    @PostMapping(value = "/SysFile/upload", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<?> upload(@RequestPart MultipartFile files);


    class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public Encoder feignFormEncoder() {

            return new FeignMultifileEncoder(new SpringEncoder(messageConverters));
        }

        class FileFeignClientFallback implements FileFeignClient {

            private Response callback() {

                final Map<String, Collection<String>> headersMap = new LinkedHashMap<>();
                final List<String> valueList = Collections.singletonList("application/json");
                headersMap.put("Content-Type", valueList);

                return Response
                        .builder()
                        .status(200)
                        .headers(headersMap)
                        .request(
                                Request.create(Request.HttpMethod.GET, "/api", Collections.emptyMap(), null,
                                        StandardCharsets.UTF_8)).body(JSONObject.toJSONString(R.error("文件服务异常")), StandardCharsets.UTF_8).build();
            }

            @Override
            public R<?> upload(MultipartFile files) {
                return R.error("文件服务异常");
            }
        }
    }
}
