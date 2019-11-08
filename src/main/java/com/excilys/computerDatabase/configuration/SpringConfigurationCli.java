package com.excilys.computerDatabase.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@ComponentScan(basePackages = { 
		"com.excilys.computerDatabase.dao",
		"com.excilys.computerDatabase.mapper",
		"com.excilys.computerDatabase.servelet",
		"com.excilys.computerDatabase.service",
		"com.excilys.computerDatabase.validator",
		"com.excilys.computerDatabase.page",
		"com.excilys.computerDatabase.main",
		"com.excilys.computerDatabase.controller"
})
@PropertySource({ "classpath:/db.properties" })
public class SpringConfigurationCli {

    
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
        
}
