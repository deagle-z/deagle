package com.zw.provider.major.elas.dao;

import com.zw.provider.major.elas.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserReponsitory extends ElasticsearchRepository<User, String> {
}
