package com.digipay.product.cardmanagmentservice.configs;

import com.digipay.product.cardmanagmentservice.configs.json.LocalDateDeserializer;
import com.digipay.product.cardmanagmentservice.configs.json.LocalDateSerializer;
import com.digipay.product.cardmanagmentservice.configs.json.LocalDateTimeDeserializer;
import com.digipay.product.cardmanagmentservice.configs.json.LocalDateTimeSerializer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

//        SimpleModule module = new SimpleModule();
//        module.addSerializer(LocalDate.class, new LocalDateSerializer());
//        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
//
//        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
//        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
//
//        mapper.registerModule(module);
        mapper.registerModule(new Hibernate5Module());
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

}
