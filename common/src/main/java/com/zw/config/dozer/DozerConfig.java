package com.zw.config.dozer;


import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfig {
    public Mapper dozerMapper(){
        Mapper mapper = DozerBeanMapperBuilder.create()
                //指定 dozer mapping 的配置文件(放到 resources 类路径下即可)，可添加多个 xml 文件，用逗号隔开
                .withMappingFiles("dozerBeanMapping.xml")
                .withMappingBuilder(beanMappingBuilder())
                .build();
        return mapper;
    }

    @Bean
    public BeanMappingBuilder beanMappingBuilder() {
        return new BeanMappingBuilder() {
            @Override
            protected void configure() {
                // 个性化配置添加在此
                //1： 测试所有properties，为不同名的 property 手动配置映射关系
//                mapping(StudentDomain.class, StudentVo.class)
//                        .fields("address", "addr");
                //2：关闭隐式匹配
//                mapping(StudentDomain.class, StudentVo.class, TypeMappingOptions.wildcard(false))
//                        .fields("address", "addr");
                //3：测试所有properties，为不同名的 property 手动配置映射关系，排除 mobile 字段
//                mapping(StudentDomain.class, StudentVo.class)
//                        .exclude("mobile")
//                        .fields("address", "addr");
//                mapping(AddressDomain.class, AddressVo.class)
//                        .fields("detail", "detailAddr");
                //4：测试深度索引匹配
//                mapping(StudentDomain.class, StudentVo.class)
//                        .fields("courses[0].teacherName", "counsellor");
//                //5：Dozer 开箱即用的功能之一就是类型转换，多数类型我们不需要手动转换类型，完全交给 Dozer即可
//                6：Date 和 String 不可以默认做转换，我们需要指定 date-formate 格式
//                mapping(StudentDomain.class, StudentVo.class TypeMappingOptions.dateFormat("yyyy-MM-dd"))
//                        .fields("courses[0].teacherName", "counsellor");
//                7：设置全局时间格式
//                <?xml version="1.0" encoding="UTF-8"?>
//                 <mappings xmlns="http://dozermapper.github.io/schema/bean-mapping"
//                                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
//                                xsi:schemaLocation="http://dozermapper.github.io/schema/bean-mapping http://dozermapper.github.io/schema/bean-mapping.xsd">
//                    <configuration>
//                        <!-- 默认是 true，当发生转换错误时抛出异常，停止转换，这里设置成false，如果转换错误，继续转换 -->
//                        <stop-on-errors>false</stop-on-errors>
//                        <date-format>yyyy-MM-dd HH:mm:ss</date-format>
//                    </configuration>
//                </mappings>
//                8：如果同时设置了全局/类/Field 级别的 date-format，按照优先级最高的进行格式化：Field > 类 > 全局
//                  9：当有些字段需要特殊处理的时候，我们需要实现自定义转换，也就是需要自定义 Converter
//              假设 StudentDomain.java 有 Integer 类型的 score 字段，StudentVo.java 中表示的分数则是 Enum 类型，分为 A/B/C/D 四个等级
//               10: 自定义 Converter
//                mapping(StudentDomain.class, StudentVo.class)
//                        .fields("score", "score", customConverter(ScoreConverter.class));
//                11:Dozer 可以通过实现 DozerEventListener 接口实现 mapping 的事件监听，在 mapping 的时候做全局业务
            }
        };
    }
}
