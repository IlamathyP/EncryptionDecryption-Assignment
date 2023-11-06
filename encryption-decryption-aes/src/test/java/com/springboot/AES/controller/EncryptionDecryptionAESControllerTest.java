package com.springboot.AES.controller;

import com.springboot.AES.service.EncryptionDecryptionAESService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionDecryptionAESControllerTest {


    private EncryptionDecryptionAESService encryptionDecryptionAESService = new EncryptionDecryptionAESService();
    @Test
    void createKeys_validAPIKey_success() {
        ResponseEntity responseEntity = encryptionDecryptionAESService.generateSecretKey("App1","seckey","1","QXBwMg==");
        assertEquals(responseEntity, ResponseEntity.status(HttpStatus.OK));
    }

    @Test
    void encryptMessage() {
    }

    @Test
    void decryptMessage() {
    }
}