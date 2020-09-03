package com.zw.provider.major.ioc;

/**
 * 初始化 bean
 *
 * @author zw
 * @date 2019/12/27
 */
public interface BeanDefinition {

    String SINGLETION = "SINGLETION";
    String PROTOTYPE = "PROTOTYPE";

    String SINGLETON();

    Class getBeanClass();

    String getBeanFactory();


    String getCreateBeanMethod();

    String getStaticCreateBeanMethod();

    String getStaticBeanCreateMethod();

    String getBeanInitMethodName();

    String getBeanDestoryMethodName();

    String getScope();

    boolean isSingleton();

    boolean isPrototype();

    boolean validate();
}
