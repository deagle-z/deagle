package com.zw.provider.major.business.controller;

import com.zw.provider.major.business.service.CacheService;
import com.zw.response.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 二级缓存 测试
 *
 * @author zw
 * @date 2020/12/24
 */
@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {

    private final CacheService cacheService;


    /**
     * cache test
     *
     * @author zw
     * @date 2020/12/24
     */
    @PostMapping("/test")
    public R<String> success() {
        cacheService.cacheSuccess("100001");
        return R.success("success");
    }
}
