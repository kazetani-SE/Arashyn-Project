package com.arashi.edu.arashynbe.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class JacksonConfig {

  @Bean
  @Primary
  public ObjectMapper objectMapper(){
    var mapper = new ObjectMapper();

    // Java 8 Time API
    mapper.registerModule(new JavaTimeModule());

    // Return ISO-8601 instead of timestamps
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Ignore unknown JSON properties
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    return mapper;
  }
}