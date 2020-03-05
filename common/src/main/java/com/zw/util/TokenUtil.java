package com.zw.deagle.util;

import com.zw.deagle.constants.CommonConstants;
import com.zw.deagle.auth.entity.User;
import com.zw.deagle.base.exception.BusinessException;
import com.zw.deagle.base.exception.UserInfoInvalidException;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * 生成解析token
 *
 * @author zw
 * @date 2020/2/3
 */
public class TokenUtil {
    private static final Long EXPIRE = 1200000L;
    private static final String SUBJECT = "deagle";
    private static final String APP_SECRET = "7786df7fc3a34e26a61c034d5ec8245d@#$%^&*()QWERTYUIOP{}ASDFGHJKL:ZXCXVBNM!@#$%^&*()";
    private static final String EXCHANGE_APP_SECRET = "7786df7fc3a34e26a61c034d5ec8245d@#$%^&*()QWERTYUIOP{}ASDFGHJKL:ZXCXVBNM!@#$%^&*()!@#";
    private static final String EXP_KEY = "exp";
    //JWT 三部分
    //1: Header ,Payload , Signature三部分
    //header :
    //    {
    //        "alg": "HS256",
    //            "typ": "JWT"
    //    }
    //Payload : 有效载荷 ,
    //    {
    //        registered claims : 预定义声明,非必填,包括 iss(发行人) exp(到期时间), sub(主题) aud(受众)
    //        public claims：这些可以由使用JWT的人随意定义。但为避免冲突，应在 IANA JSON Web Token Register中定义它们，
    //            或者将其定义为包含防冲突命名空间的URI。
    //        private claims：这些是自定义声明，用于在同意使用这些声明的各方之间共享信息，这些信息既没有注册也没有公开声明
    //    }
    //    Signature（签名）
    //Signature由Base64Url加密的Header、Payload再使用Header中指定的算法加密之后再和secret组成


    /**
     * 获取默认时间的token
     *
     * @param user
     * @return String
     * @author zw
     * @date 2020/2/3
     */
    public static String getToken(User user) {
        return createToken(user, APP_SECRET, EXPIRE);
    }

    public static String getExchangeToken(User user) {
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return createToken(user, EXCHANGE_APP_SECRET, null);
    }


    public static User parseToken(String token) {
        return paresToken(token, APP_SECRET);
    }

    public static User parseExchangeToken(String token) {
        return paresToken(token, EXCHANGE_APP_SECRET);
    }

    public static User paresToken(String token, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token).getBody();
        User user = new User();
        if (claims.containsKey(EXP_KEY)) {
            Long aLong = claims.get(EXP_KEY, Long.class);
            if (System.currentTimeMillis() <= aLong) {
                if (claims.containsKey(CommonConstants.TOKEN_USERNAME) &&
                        StringUtils.isNoneEmpty(claims.get(CommonConstants.TOKEN_USERNAME, String.class))) {
                    user.setUsername(claims.get(CommonConstants.TOKEN_USERNAME, String.class));
                } else {
                    throw new BusinessException("解析失败");
                }
            } else {
                throw new UserInfoInvalidException("用户信息已过期,请重新登录");
            }
        }
        return user;
    }


    public static String createToken(User user, String secret, Long expire) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new BusinessException("获取用户信息失败!");
        }
        //签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


        JwtBuilder jwtBuilder = Jwts.builder().setId("id")
                .setIssuedAt(new Date())
                .setSubject(SUBJECT)
                .setIssuer("zw")
                .claim(CommonConstants.TOKEN_USERNAME, user.getUsername())
                .claim("EXP", user.getUsername())
                .signWith(signingKey);
        if (expire != null) {
            jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                    .claim(EXP_KEY, System.currentTimeMillis() + EXPIRE);
        }
        return jwtBuilder.compact();
    }
}
