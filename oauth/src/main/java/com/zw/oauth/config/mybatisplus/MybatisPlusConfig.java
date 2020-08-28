package com.zw.oauth.config.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 *
 * @author zw
 * @date 2020/8/6
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {
	/**
	 * mybatis-plus SQL执行效率插件【生产环境可以关闭】
	 */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }

	/**
	 * 分页插件，自动识别数据库类型 多租户，请参考官网【插件扩展】
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}

//    @Bean
//    public MetaObjectHandler metaObjectHandler() {
//        return new MPMetaObjectHandler();
//    }
//
//	/**
//	 * 注入主键生成器
//	 */
//	@Bean
//	public IKeyGenerator keyGenerator() {
//		return new H2KeyGenerator();
//	}

	/**
	 * 注入sql注入器(此处注入了逻辑删除注入器)
	 */
//	@Bean
//	public ISqlInjector sqlInjector() {
//		return new LogicSqlInjector();
//	}

}
