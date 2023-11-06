package com.springboot.AES.service;


import com.springboot.AES.entity.SecretKeyEntity;
import com.springboot.AES.repository.SecretKeyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;


@Service
public class EncryptionDecryptionAESService {
    private  final int KEY_LENGTH = 256;
    private  final int ITERATION_COUNT = 65536;

    @Autowired
    private SecretKeyRepository secretKeyRepository;
    public static Map<String,SecretKey> secretKeyMap = new HashMap<>();

    String algorithm = "AES";
    int KEY_SIZE =  256;
    //int ITERATION_COUNT = 65536;
    String salt ="mysalt";
    SecretKeyEntity secretKeyEntity = new SecretKeyEntity();
    @Autowired
    private WebClient webClient;
    @Value("${authService.base.url}")
    private String authServiceBaseUrl;

    int T_LEN = 128;

   // Cipher encryptionCipher = Cipher.getInstance("AES/GCM/Nopadding");
   // Cipher decryptionCipher = Cipher.getInstance("AES/GCM/Nopadding");

    private Cipher getMutualCipher() throws Exception {
        Cipher cipher = Cipher.getInstance("AES/GCM/Nopadding");
        return cipher;// cipher.doFinal(pass.getBytes());
    }


    public ResponseEntity generateSecretKey(String appName,String secretKeyName,String userId,String api_key){
        //validateUserAndToken(userId,AppName,ApiKey);

        //String AppName = Base64.getDecoder().decode()
        String queryString = "/authorizeUser/validateUserAndToken?userId="+userId+"&AppName="+appName+"&ApiKey="+api_key;
        // authservicebaseurl = authServiceBaseUrl + queryString;

        System.out.println(authServiceBaseUrl + queryString);
        String response = webClient.get()
                .uri(authServiceBaseUrl + queryString)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(response);
        System.out.println(response.substring(1,4));
        if(response.substring(1,4).equals("200")) {
            try {
                if (secretKeyName == null || secretKeyName == "") {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }

                //SecretKey key;
                /*KeyGenerator generator = KeyGenerator.getInstance(algorithm);
                generator.init(KEY_SIZE);
                key = generator.generateKey();
                //secretKeyMap.put(secretKeyName, key);*/
                String secretKeyValue = UUID.randomUUID().toString();

                System.out.println("Secret Key Generated: " );
                SecretKeyEntity secretKeyEntity = new SecretKeyEntity();
                secretKeyEntity.setSecretKeyName(secretKeyName);
                secretKeyEntity.setSecretKeyValue(secretKeyValue);
                secretKeyRepository.save(secretKeyEntity);
            } catch (Exception ex) {
                return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
            }
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }
    String decrypt(String strToDecrypt, String secretKey, String salt) {

        try {

            byte[] encryptedData = Base64.getDecoder().decode(strToDecrypt);
            byte[] iv = new byte[16];
            System.arraycopy(encryptedData, 0, iv, 0, iv.length);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

            byte[] cipherText = new byte[encryptedData.length - 16];
            System.arraycopy(encryptedData, 16, cipherText, 0, cipherText.length);

            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText, "UTF-8");
        } catch (Exception e) {
            // Handle the exception properly
            e.printStackTrace();
            return null;
        }
    }
    String encrypt(String strToEncrypt, String secretKey, String salt) {
        try {

            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), ITERATION_COUNT, KEY_LENGTH);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);

            byte[] cipherText = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
            byte[] encryptedData = new byte[iv.length + cipherText.length];
            System.arraycopy(iv, 0, encryptedData, 0, iv.length);
            System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            // Handle the exception properly
            e.printStackTrace();
            return null;
        }

    }
 public String encryptMessage(String appName,String plainMessage, String secretKeyName,String userId,String api_key)throws RuntimeException{
     String queryString = "/authorizeUser/validateUserAndToken?userId="+userId+"&AppName="+appName+"&ApiKey="+api_key;
     // authservicebaseurl = authServiceBaseUrl + queryString;

     System.out.println(authServiceBaseUrl + queryString);
     String response = webClient.get()
             .uri(authServiceBaseUrl + queryString)
             .retrieve()
             .bodyToMono(String.class)
             .block();
     System.out.println(response);
     System.out.println(response.substring(1,4));
     if(response.substring(1,4).equals("200")) {
         try {
             secretKeyEntity = secretKeyRepository.findBysecretKeyName(secretKeyName);
             System.out.println("secretKeyEntity.getSecretKe: " +  secretKeyEntity.getSecretKeyValue() );
             String encryptedString = encrypt(plainMessage, secretKeyEntity.getSecretKeyValue(), salt);
             if (encryptedString != null) {
                 System.out.println("Encrypted: " + encryptedString);
             } else {
                 System.err.println("Encryption failed.");
             }
             return encryptedString;
            
         }  catch (Exception e) {
             throw new RuntimeException(e);
         }
     }else{
         return "User or Api Key validation failed";
     }

 }

    public String decryptMessage(String appName,String encrytedMessage, String secretKeyName,String userId,String api_key)throws RuntimeException {
        String queryString = "/authorizeUser/validateUserAndToken?userId="+userId+"&AppName="+appName+"&ApiKey="+api_key;
        // authservicebaseurl = authServiceBaseUrl + queryString;

        //System.out.println(authServiceBaseUrl + queryString);
        String response = webClient.get()
                .uri(authServiceBaseUrl + queryString)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        //System.out.println(response);
        //System.out.println(response.substring(1,4));
        if(response.substring(1,4).equals("200")) {
            try {
               
                secretKeyEntity = secretKeyRepository.findBysecretKeyName(secretKeyName);
                System.out.println("secretkey :"+secretKeyEntity.getSecretKeyValue());
                String decryptedString = decrypt(encrytedMessage, secretKeyEntity.getSecretKeyValue(), salt);
                if (decryptedString != null) {
                    System.out.println("Decrypted: " + decryptedString);
                } else {
                    System.err.println("Decryption failed.");
                }
                return decryptedString;
               

            } catch (RuntimeException ex) {
                ex.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR).toString();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }else{
            return  "User or Api Key validation failed";
        }

    }

    private String encode(byte[] data){

        return Base64.getEncoder().encodeToString(data);
    }

    private byte[] decode(String encryptedData){

        return Base64.getDecoder().decode(encryptedData);
    }


}
