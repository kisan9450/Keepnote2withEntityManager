package com.stackroute.keepnote.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/*This class will contain the application-context for the application.
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the
 *                  class can be used by the Spring IoC container as a source of
 *                  bean definitions
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *
 * */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.stackroute.keepnote"}, excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class) })

public class ApplicationContextConfig {


	/*
	 * Define the bean for DataSource. In our application, we are using MySQL as the
	 * dataSource. To create the DataSource bean, we need to know: 1. Driver class
	 * name 2. Database URL 3. UserName 4. Password
	 */
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/notedb");
		dataSource.setUsername("root");
		dataSource.setPassword("9304083096s");
		return dataSource;
	}

	/*
	 * Define the bean for SessionFactory. Hibernate SessionFactory is the factory
	 * class through which we get sessions and perform database operations.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.stackroute.keepnote" });
		JpaVendorAdapter jpaVendorAdapter=new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(jpaVendorAdapter);
		em.setJpaProperties(hibernateProperties());
		return em;
	}

	@Bean
	public Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		properties.put("hibernate.hbm2ddl.auto","update");
		return properties;
	}

	/*
	 * Define the bean for Transaction Manager. HibernateTransactionManager handles
	 * transaction in Spring. The application that uses single hibernate session
	 * factory for database transaction has good choice to use
	 * HibernateTransactionManager. HibernateTransactionManager can work with plain
	 * JDBC too. HibernateTransactionManager allows bulk update and bulk insert and
	 * ensures data integrity.
	 */

	@Bean
	@Autowired
	public PlatformTransactionManager transactionManager(EntityManagerFactory em) {
		JpaTransactionManager jpaTransactionManager=new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(em);
		return jpaTransactionManager	;
	}
}
