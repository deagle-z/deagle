package com.zw.provider.major.ioc;

public interface BeanDefinitionRegistery {

    void register(BeanDefinition beanDefinition, String beanName);

    BeanDefinition getBeanDefinition(String beanName);

    boolean containsBeanDefinition(String beanName);
}
