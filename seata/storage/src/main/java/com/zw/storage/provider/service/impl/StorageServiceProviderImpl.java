package com.zw.storage.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zw.api.storage.StorageService;
import com.zw.response.R;
import com.zw.storage.business.entity.StorageEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
  * dubbo test
  * @date 2020/3/17
  * @author zw
*/
@Service(version = "1.0.0",group = "deagle",interfaceName = "StorageService",timeout = 3000)
public class StorageServiceProviderImpl implements StorageService {

    @Autowired
    private com.zw.storage.business.service.StorageService storageService;

    @Override
    public R minusOne(String code) {
        StorageEntity storageEntity = new StorageEntity();
        storageEntity.setCommodityCode(code);
        storageService.save(storageEntity);
        return R.success("success");
    }

//    @Service(version = "1.0.0",protocol = "${dubbo.protocol.id}",
//            application = "${dubbo.application.id}",registry = "${dubbo.registry.id}",
//            timeout = 3000)
}
