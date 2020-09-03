package com.zw.provider.major.ioc.impl;

import com.zw.provider.major.ioc.BeanDefinition;
import com.zw.provider.major.ioc.BeanDefinitionRegistery;
import com.zw.provider.major.ioc.BeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
  * cs
  * @date 2019/12/28
  * @author zw
*/
@Slf4j
public class DefaultBeanFactory  implements BeanFactory, Closeable , BeanDefinitionRegistery {

    //ConcurrentHashMap应对并发环境
    private Map<String, BeanDefinition> bdMap = new ConcurrentHashMap<>();

    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 获取bean
     *
     * @param beanName@return
     * @date 2019/12/27
     */
    @Override
    public Object getBean(String beanName) {
        if(!beanMap.containsKey(beanName)){
            log.info("[" + beanName + "]不存在");
        }
        //获取definition
        Object instance = beanMap.get(beanName);

        if(instance != null){
            return instance;
        }

        //不存在则进行创建
        if(!this.bdMap.containsKey(beanName)){
            log.info("不存在名为：[" + beanName + "]的bean定义");
        }

        BeanDefinition bd = this.bdMap.get(beanName);

        Class<?> beanClass = bd.getBeanClass();

        if(beanClass != null){
            instance = createBeanByConstruct(beanClass);
            if(instance == null){
                instance = createBeanByStaticFactoryMethod(bd);
            }
        }else if(instance == null && StringUtils.isNotBlank(bd.getStaticCreateBeanMethod())){
            instance = createBeanByFactoryMethod(bd);
        }

        this.doInit(bd, instance);

        if(instance != null && bd.isSingleton()){
            beanMap.put(beanName, instance);
        }

        return instance;
    }


    private void doInit(BeanDefinition bd, Object instance) {
        Class<?> beanClass = instance.getClass();
        if (StringUtils.isNotBlank(bd.getBeanInitMethodName())) {
            try {
                Method method = beanClass.getMethod(bd.getBeanInitMethodName(), null);
                method.invoke(instance, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {

    }


    @Override
    public void register(BeanDefinition bd, String beanName) {
        Assert.notNull(bd,"beanDefinition 不可为空");
        Assert.notNull(beanName,"beanName 不可为空");
        if(!bd.validate()){
            log.info("BeanDefinition 不合法");
        }
        if (bdMap.containsKey(beanName)) {
            log.info("{}已存在",beanName);
        }else{
            bdMap.put(beanName, bd);
        }

    }


    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        if(!bdMap.containsKey(beanName)){
            log.info("[" + beanName + "]不存在");
        }
        return bdMap.get(beanName);
    }

    /**
     * Check if this registry contains a bean definition with the given name.
     *
     * @param beanName the name of the bean to look for
     * @return if this registry contains a bean definition with the given name
     */
    @Override
    public boolean containsBeanDefinition(String beanName) {
        return bdMap.containsKey(beanName);
    }


    /**
     * 构造方法创建实例
     * @param beanClass
     * @return
     */
    private Object createBeanByConstruct(Class<?> beanClass) {
        Object instance = null;
        try {
            instance = beanClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 普通工厂方法创建实例
     * @param bd
     * @return
     */
    private Object createBeanByFactoryMethod(BeanDefinition bd) {
        Object instance = null;
        try {
            //获取工厂类
            Object factory = getBean(bd.getBeanFactory());
            //获取创建实例的方法
            Method method = factory.getClass().getMethod(bd.getCreateBeanMethod());
            //执行方法
            instance = method.invoke(factory, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 静态方法创建实例
     * @param bd
     * @return
     */
    private Object createBeanByStaticFactoryMethod(BeanDefinition bd) {
        Object instance = null;
        try {
            Class<?> beanClass = bd.getBeanClass();
            //获取创建实例的方法
            Method method = beanClass.getMethod(bd.getStaticCreateBeanMethod());
            instance = method.invoke(beanClass, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

}
