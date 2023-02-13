package com.trackerimmo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigProperties {

	@Bean
    @ConfigurationProperties(prefix = "kafka")
    public KafkaProperties item() {
        return new KafkaProperties();
    }
	
	@Bean
    @ConfigurationProperties(prefix = "milvus")
    public MilvusProperties milvusPros() {
        return new MilvusProperties();
    }
}
