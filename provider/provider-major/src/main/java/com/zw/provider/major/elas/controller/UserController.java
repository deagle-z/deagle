package com.zw.provider.major.elas.controller;

import com.zw.provider.major.elas.dao.UserReponsitory;
import com.zw.provider.major.elas.entity.User;
import com.zw.response.R;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserReponsitory userReponsitory;
    @Resource
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @PostMapping("/add")
    public R<User> add(@RequestBody User user){
        user = userReponsitory.save(user);
        System.out.println(11111);
        System.out.println(user.toString());
        System.out.println(11111);
        return new R(user);
    }


    @PostMapping("/findAll")
    public R<User> findAll(@RequestBody User user){
        final Iterable<User> all = userReponsitory.findAll();
        List<User> list=new ArrayList<>(10);
        all.forEach(list::add);
        return new R(list);
    }

    @PostMapping("/page")
    public R<User> page(@RequestBody User user){
        final NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder().withPageable(PageRequest.of(user.getCurrentPage(), user.getSize()));
        final NativeSearchQuery build = queryBuilder.build();
        return new R(elasticsearchTemplate.queryForPage(build,User.class));
    }

}
