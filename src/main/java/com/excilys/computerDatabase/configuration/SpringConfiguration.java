package com.excilys.computerDatabase.configuration;

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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = { "com.excilys.computerDatabase.configuration", "com.excilys.computerDatabase.dao",
		"com.excilys.computerDatabase.mapper", "com.excilys.computerDatabase.servelet",
		"com.excilys.computerDatabase.service", "com.excilys.computerDatabase.validator",
		"com.excilys.computerDatabase.page", "com.excilys.computerDatabase.main",
		"com.excilys.computerDatabase.controller" })
@PropertySource({ "classpath:/db.properties" })
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
		AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
		webCtx.register(SpringConfiguration.class, SpringMvcConfiguration.class);
		webCtx.setServletContext(ctx);
		// Init dispatcher servlet
		ServletRegistration.Dynamic servlet = ctx.addServlet("springapp", new DispatcherServlet(webCtx));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
	}

}
