package com.xpeppers.phonedirectory.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.xpeppers.phonedirectory")
@Import({WebAppConfig.class, DatastoreConfig.class})
public class PhoneDirectoryConfig {

  public static final String GLOBAL_DATE_FORMAT = "dd-MM-yyyy";

  @Bean
  public Gson gson() {
    return new GsonBuilder()
      .serializeNulls()
      .setPrettyPrinting()
      .setDateFormat(GLOBAL_DATE_FORMAT)
      .serializeSpecialFloatingPointValues()
      .create();
  }

}
