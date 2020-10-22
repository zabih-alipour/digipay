package com.digipay.product.cardmanagmentservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.logging.Logger;

@Configuration
public class AppConfig {

    public AppConfig(@Value("${spring.datasource.url}") String url){
        Logger.getGlobal().info("Mysql url: " + url);
    }

    @Bean
    public String tt(){
        return "ssss";
    }
}
