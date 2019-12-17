package com.zw.provider.major.mongo.service.impl;

import com.zw.provider.major.mongo.entity.ProcessInstance;
import com.zw.provider.major.mongo.service.ProcessInstanceService;
import com.zw.util.base.QueryPage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author zw
 * @description
 * @date 2019/12/17
 */
@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService {


    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public ProcessInstance insert(ProcessInstance processInstance) {
        final Date date = new Date();
        processInstance.setCreateTime(date);
        processInstance.setUpdateTime(date);
        return mongoTemplate.insert(processInstance);
    }

    public void pagingQuery(QueryPage<ProcessInstance> page) {
        Query query = new Query();
    }

}
