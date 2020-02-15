package com.xiaoke1256.spring.mytest;

import java.util.Properties;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class SpringbootApplication  {
	@Value("${spring.datasource.drive-class-name}")
	private String dsDriveCLassName;
	
	@Value("${spring.datasource.url}")
	private String dsUrl;
	
	@Value("${spring.datasource.username}")
	private String dsUsername;
	
	@Value("${spring.datasource.password}")
	private String dsPassword;
	
	@Value("${spring.jpa.database-platform}")
	private String dsDialect;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName(dsDriveCLassName);
		ds.setUrl(dsUrl);
		ds.setUsername(dsUsername);
		ds.setPassword(dsPassword);
		
		return ds;
	}
//	
//	@Bean
//	public FactoryBean<SessionFactory> sessionFactory() {
//		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
//		sessionFactoryBean.setDataSource(dataSource());
//		sessionFactoryBean.setPackagesToScan("com.xiaoke1256.spring.mytest.bo");
//		sessionFactoryBean.setMappingDirectoryLocations(new ClassPathResource("/com/xiaoke1256/spring/mytest"));
//		Properties hibernateProperties = new Properties();
//		hibernateProperties.put("hibernate.dialect", dsDialect);
//		sessionFactoryBean.setHibernateProperties(hibernateProperties);
//		return sessionFactoryBean;
//	}
//	
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//		try {
//			transactionManager.setSessionFactory(sessionFactory().getObject());
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		return transactionManager;
//	}
}
