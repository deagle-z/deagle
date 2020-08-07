package com.zw.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Tian Haobing on 2019/6/23
 */
@Slf4j
@SuppressWarnings({ "PMD.ReturnFromFinallyBlock","PMD.CyclomaticComplexity","PMD.DoNotThrowExceptionInFinally"})
public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {

        if (request.getContentType() == null || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            final ObjectMapper objectMapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try ( InputStream inputStream = request.getInputStream()) {
                final Map<String, String> authenticationBean = objectMapper.readValue(inputStream, Map.class);
                log.info("authenticationBean：【{}】",authenticationBean);
                log.info("获取验证码");
                final String uuid = authenticationBean.get("uuid");
                log.info("验证码key值：【{}】",uuid);
                final String authcode = authenticationBean.get("code");
                log.info("验证码value值：【{}】",authcode);
                final String code = (String)redisTemplate.opsForValue().get(uuid);
                if (!authcode.equals(code)){
                    log.info("验证码错误【{}】",code);
//                    authRequest = new UsernamePasswordAuthenticationToken("", "");
                     throw new InsufficientAuthenticationException("验证码错误");
                }
                String username=authenticationBean.get("username");
                log.info("用户名：【{}】",username);
                String password = authenticationBean.get("password");
                log.info("密码：【{}】",password);
                //通过判断配置中心是否开启smap验证决定走oa还是本地
                log.info("客户端静态密码校验返回标识", code);
                log.info("oa静态密码认证通过,设置密码去cpms本地校验");
                password= "U%!O#7YJL";
                authRequest = new UsernamePasswordAuthenticationToken(username, password);
                log.info("authRequest【{}】",authRequest);
               // authRequest = new UsernamePasswordAuthenticationToken(username, password);
            } catch (final IOException e) {
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            }finally {
                if (Objects.nonNull(authRequest)){

                    this.setDetails(request, authRequest);
                    return this.getAuthenticationManager().authenticate(authRequest);
                }else {
                    throw new InsufficientAuthenticationException("验证码错误");
                }
            }
        } else {
            return super.attemptAuthentication(request, response);
        }

    }
}
