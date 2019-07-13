package com.idc.config;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

@Configuration
public class DataSourceConfig {
	
	@Bean("oauthDataSource")
	@ConfigurationProperties(prefix="spring.server.oauth.datasource")
	public DataSource getOauthDataSource() {
		return DataSourceBuilder.create(BasicDataSource.class.getClassLoader()).build();
	}
	
	@Bean("userDataSource")
	@ConfigurationProperties(prefix="spring.server.user.datasource")
	public DataSource getUserDataSource() {
		return DataSourceBuilder.create(BasicDataSource.class.getClassLoader()).build();
	}
	
	
	@Bean("oauthSqlSessionFactory")
	@Resource(name="oauthDataSource")
	public SqlSessionFactory getOauthSqlSessionFactory(DataSource oauthDataSource) throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
		sf.setDataSource(oauthDataSource);
		sf.setConfigLocation(resolver.getResource("classpath:mybatis/oauth/config/mybatis-config.xml"));
		sf.setMapperLocations(resolver.getResources("classpath:mybatis/oauth/mapper/*/*.xml"));
		return sf.getObject();
	}
	
	@Bean("userSqlSessionFactory")
	@Resource(name="userDataSource")
	public SqlSessionFactory getUserSqlSessionFactory(DataSource userDataSource) throws Exception {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		SqlSessionFactoryBean sf = new SqlSessionFactoryBean();
		sf.setDataSource(userDataSource);
		sf.setConfigLocation(resolver.getResource("classpath:mybatis/user/config/mybatis-config.xml"));
		sf.setMapperLocations(resolver.getResources("classpath:mybatis/user/mapper/*/*.xml"));
		return sf.getObject();
	}
}