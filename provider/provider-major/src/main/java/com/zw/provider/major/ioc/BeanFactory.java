package com.zw.provider.major.ioc;

public interface BeanFactory {

    /**
     * 获取bean
     *
     * @param
     * @return
     * @date 2019/12/27
     */
    Object getBean(String beanName);
}
