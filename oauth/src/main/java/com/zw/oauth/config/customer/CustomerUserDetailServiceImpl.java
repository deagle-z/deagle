package com.zw.oauth.config.customer;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.dozermapper.core.DozerBeanMapper;
import com.zw.constant.SystemConstant;
import com.zw.oauth.business.pojo.dto.UserDetailsDto;
import com.zw.oauth.business.pojo.entity.UserEntity;
import com.zw.oauth.business.service.UserService;
import com.zw.util.ThreadLocalMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


/**
 * 自定义用户实现类
 *
 * @author zw
 * @date 2020/8/26
 */
@Component
@Slf4j
public class CustomerUserDetailServiceImpl implements UserDetailsService {

    @Resource
    UserService userService;
    @Resource
    DozerBeanMapper dozerMapper;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        final Map<SFunction<UserEntity, ?>, Object> paramMap = UserService.paramMap();
        paramMap.put(UserEntity::getAccount, account);
        final UserEntity userEntity = userService.selectOne(paramMap);
        final UserDetailsDto userDetails = new UserDetailsDto();
        dozerMapper.map(userEntity,userDetails);
        log.info("loadUserByUsername 校验用户信息：{}",userDetails.toString());
        ThreadLocalMap.put(SystemConstant.Token.TOKEN_USER,userDetails);
        return userDetails;
    }
}
