package com.excilys.computerDatabase.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.excilys.computerDatabase.configuration.SpringMvcConfiguration;

@Configuration
@ComponentScan(basePackages = { "com.excilys.computerDatabase.dao",
		"com.excilys.computerDatabase.mapper",
		"com.excilys.computerDatabase.service",
		"com.excilys.computerDatabase.validator",
		"com.excilys.computerDatabase.page",
		"com.excilys.computerDatabase.controller" })
@PropertySource({ "classpath:/db.properties" })
@EnableTransactionManagement
@EnableWebMvc
public class SpringConfiguration implements WebApplicationInitializer {

	@Autowired
	private Environment env;

	@Bean
	public DataSource getMySQLDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUrl(env.getProperty("jdbcUrl"));
		dataSource.setUsername(env.getProperty("dataSource.user"));
		dataSource.setPassword(env.getProperty("dataSource.password"));
		return dataSource;
	}

	@Override
	public void onStartup(ServletContext ctx) throws ServletException {
		// Init application context
		System.out.println("start");
		AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
		webCtx.register(SpringConfiguration.class, SpringMvcConfiguration.class);
		webCtx.setServletContext(ctx);
		// Init dispatcher servlet
		ServletRegistration.Dynamic servlet = ctx.addServlet("springapp", new DispatcherServlet(webCtx));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	    JpaTransactionManager transactionManager = new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(emf);
	 
	    return transactionManager;
	}
	 
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
	    return new PersistenceExceptionTranslationPostProcessor();
	}
	
	 @Bean
	   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	      LocalContainerEntityManagerFactoryBean em 
	        = new LocalContainerEntityManagerFactoryBean();
	      em.setDataSource(getMySQLDataSource());
	      em.setPackagesToScan(new String[] { "com.excilys.computerDatabase.entity" });
	 
	      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	      em.setJpaVendorAdapter(vendorAdapter);
	      //em.setJpaProperties(additionalProperties());
	 
	      return em;
	   }
	 
	 Properties additionalProperties() {
		    Properties properties = new Properties();
		    properties.setProperty("hibernate.hbm2ddl.auto", "update");
		    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		        
		    return properties;
		}

}
