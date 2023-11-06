package com.crypto.SpringSecurity.controller;

import com.crypto.SpringSecurity.model.Access;
import com.crypto.SpringSecurity.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
//@RequestMapping("/authorizeUser")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;
    @GetMapping("/authorizeUser/isAdmin")
    public String authorizeUser(@RequestParam(name="userId") String userId){
        //authorizationService.
        //authorizationService.findUserAccessListByID(userId);
        ResponseEntity<String> response = authorizationService.isAdmin(userId);
       // System.out.println(response);
        return response.toString();
    }

    @GetMapping("/authorizeUser/validateUserAndToken")
    public String validateUserAndToken(@RequestParam(name="userId") String userId, @RequestParam(name ="AppName") String AppName, @RequestParam(name ="ApiKey") String ApiKey){
        //authorizationService.
        //authorizationService.findUserAccessListByID(userId);
        ResponseEntity<String> response = authorizationService.validateUserAndToken(userId,AppName,ApiKey);
        // System.out.println(response);
        return response.toString();
    }

    @PostMapping("/addApp")
    public String addAppToAccessTable(@RequestParam(name="appId") String appId, @RequestParam(name ="AppName") String AppName, @RequestParam(name="apiKey") String apiKey, @RequestParam(name="userId") String userId){
        //authorizationService.
        //authorizationService.findUserAccessListByID(userId);
        ResponseEntity<String> response = authorizationService.addAppToAccessTable(appId,AppName,apiKey,userId);
        // System.out.println(response);
        return response.toString();
    }

    /**@GetMapping("/getAcessDetails")
    public List<Access> getAcessDetails(){
        return authorizationService.getAccessDetails();
    }**/

}
