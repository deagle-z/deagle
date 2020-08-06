package com.zw.auth.security.authentication;

import com.zw.auth.business.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
  * 实现UserDetailsService 重写 loadUserByUsername方法 实际就是获取用户的信息
  * 配合 authenticationManager 用于密码模式的验证
  *
  * @date 2020/3/5
  * @author zw
*/
@Slf4j
@Component
public class AuthDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
//        Wrappers.<UserEntity>lambdaQuery().eq(UserEntity::getAccount)
//        final User user = userService.getOne();
//        final SecurityDetail securityDetail = new SecurityDetail();
//        if (StringUtils.isEmpty(user.getPassword())) {
//            throw new BusinessException("用户名不存在！");
//        }
//        try {
//            copyProperties(user,securityDetail);
//            //缺少设置 securityDetail.setAuthorities()
//        } catch (Exception e) {
//            log.error("loadUserByUsername,复制数据失败！",e);
//        }
//        log.info("AuthUserDetailService::getUserByName::responseDto[{}]", securityDetail);

        //从数据库获取菜单信息
//        final List<Menu> menus = authDto.getMenus();
//        final List<GrantedAuthority> authList= Lists.newArrayList();
//        if(menus.size()!=0){
//            for(final Menu menu: menus){
//                final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(menu.getMenuLink());
//                authList.add(grantedAuthority);
//            }
//        }
//        final Collection<GrantedAuthority> menuLinks =authList;
//        return securityDetail;
        return null;

    }
}
