package com.springboot.AES.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class EncryptionDecryptionConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }
}
