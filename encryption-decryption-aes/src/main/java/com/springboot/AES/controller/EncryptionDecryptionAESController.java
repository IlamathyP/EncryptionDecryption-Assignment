package com.springboot.AES.controller;

import com.springboot.AES.service.EncryptionDecryptionAESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EncryptionDecryptionAESController {

    @Autowired
    private EncryptionDecryptionAESService encryptionDecryptionAESService;



    @PostMapping("/generate")
    public String createKeys(@RequestParam(name = "appName") String appName, @RequestParam(name = "secretKeyName")String secretKeyName,@RequestParam(name = "userId") String userId,@RequestParam(name = "api_key")String api_key){
        ResponseEntity<String> responseEntity = encryptionDecryptionAESService.generateSecretKey(appName,secretKeyName,userId,api_key);
     return responseEntity.toString();
    }

    @PostMapping("/encrypt")
        public String encryptMessage(@RequestParam(name = "appName") String appName,@RequestParam(name = "message") String message, @RequestParam(name = "secretKeyName") String secretKeyName,@RequestParam(name = "userId") String userId,@RequestParam(name = "api_key")String api_key){

        return encryptionDecryptionAESService.encryptMessage(appName,message,secretKeyName,userId,api_key);
    }

    @PostMapping("/decrypt")
    public String decryptMessage(@RequestParam(name = "appName") String appName,@RequestParam(name = "encryptedmessage") String encryptedmessage, @RequestParam(name = "secretKeyName") String secretKeyName,@RequestParam(name = "userId") String userId,@RequestParam(name = "api_key")String api_key)
    {
        return encryptionDecryptionAESService.decryptMessage(appName,encryptedmessage,secretKeyName,userId,api_key);
    }



}
