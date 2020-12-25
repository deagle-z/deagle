
package com.zw.storage.business.controller;

import com.zw.storage.business.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/storages")
public class StorageController {

    @Autowired
    private StorageService storageService;



}
