package com.zw.oauth.config.customer;

import com.zw.constant.SystemConstant;
import com.zw.oauth.business.pojo.dto.UserDetailsDto;
import com.zw.oauth.util.EncryptAndDecryptUtils;
import com.zw.util.ThreadLocalMap;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


/**
 * 用于oauth2用户的密码matches
 *
 * @author zw
 * @date 2020/8/27
 */
@Component
public class CustomerPasswordEncoder implements PasswordEncoder {


        @Override
        public String encode(CharSequence charSequence) {
            return charSequence.toString();
        }

        @Override
        public boolean matches(CharSequence charSequence, String encodePassword) {
            final String loginPassword = charSequence.toString();
            final UserDetailsDto userDetails = (UserDetailsDto) ThreadLocalMap.get(SystemConstant.Token.TOKEN_USER);
            if(null == userDetails){
                return charSequence.toString().equals(encodePassword);
            }else{
                return EncryptAndDecryptUtils.md5Encrypt(loginPassword + "_" + userDetails.getPasswordSalt()).equals(encodePassword);
            }
        }
}
