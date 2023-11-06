package com.springboot.AES.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "authService.base.url=http://localhost:8090",
})
class EncryptionDecryptionAESServiceTest {

    String appName= "App1";
    String secretKeyName = "key1";

    String api_key = "aW50ZXJlc3Q=";



    @Value("${authService.base.url}")
    String authServiceBaseUrl;
    @Test
    void generateSecretKey_success() {



    }

    private void assertThat(boolean xxSuccessful) {
    }

    @Test
    void encryptMessage_sucess() {
    }

    @Test
    void decryptMessage_sucess() {
    }
}