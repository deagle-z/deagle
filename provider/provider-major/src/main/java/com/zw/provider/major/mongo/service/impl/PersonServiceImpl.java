package com.zw.provider.major.mongo.service.impl;

import com.zw.provider.major.mongo.entity.Person;
import com.zw.provider.major.mongo.service.PersonService;
import com.zw.base.DeaglePageable;
import com.zw.base.QueryPage;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * test
 *
 * @author zw
 * @date 2019/12/18
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public Person insert(Person p) {
        return mongoTemplate.insert(p);
    }

    @Override
    public List<? extends Person> find(Person person) {
        Query query = new Query(Criteria.byExample(person));
        return mongoTemplate.find(query, person.getClass());
    }

    @Override
    public Object pagination(QueryPage<Person> page) {
        Query query = new Query();
        DeaglePageable deaglePageable = new DeaglePageable(page, "createTime");
        return mongoTemplate.find(query.with(deaglePageable), Person.class);
    }


}
