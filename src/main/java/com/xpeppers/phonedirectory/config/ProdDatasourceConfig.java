package com.xpeppers.phonedirectory.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:postgresql_prod.properties")
@Profile("prod")
public class ProdDatasourceConfig {
  private static final Logger logger = LoggerFactory.getLogger(ProdDatasourceConfig.class);

  @Value("${postgresql.driver}")
  private String driver;

  @Value("${postgresql.url}")
  private String url;

  @Value("${postgresql.user}")
  private String user;

  @Value("${postgresql.password}")
  private String password;

  @Bean(destroyMethod = "close")
  public ComboPooledDataSource dataSource() {
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    try {
      dataSource.setDriverClass(driver);
      dataSource.setJdbcUrl(url);
      dataSource.setUser(user);
      dataSource.setPassword(password);
    } catch (PropertyVetoException e) {
      logger.error("Error on C3P0 DataSource building...", e);
    }
    return dataSource;
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
