package com.crypto.SpringSecurity.service;

import com.crypto.SpringSecurity.model.Access;
import com.crypto.SpringSecurity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.Map;

@Service
public class AuthorizationService {
    List<User> userList = new ArrayList<User>();
    Map<String, User> userMap = new HashMap<>();

    List<Access> accessList = new ArrayList<Access>();
    Map<String, Access> accessMap = new HashMap<>();
    AuthorizationService(){
        populateData();
    }

    private void populateData(){
        User user1 = new User("1", "John Doe", "User");
        userMap.put(user1.getUserId(), user1);
        User user2 = new User("2", "Joan Doe", "User");
        userMap.put(user2.getUserId(), user2);
        User user3 = new User("3", "Jean Doe", "User");
        userMap.put(user3.getUserId(), user3);
        User user4 = new User("4", "Joan Admin", "Admin");
        userMap.put(user4.getUserId(), user4);
        User user5 = new User("5", "Jean Admin", "Admin");
        userMap.put(user5.getUserId(), user5);



        System.out.println(userMap.toString());

        Access user1AccesstoApp1 =  new Access("1","App1",user1,"QXBwMQ==");
        accessMap.put(user1AccesstoApp1.getAppName(), user1AccesstoApp1);
        user1AccesstoApp1 =  new Access("2","App2",user2,"QXBwMg==");
        accessMap.put(user1AccesstoApp1.getAppName(), user1AccesstoApp1);
        user1AccesstoApp1 =  new Access("3","App3",user3,"QXBwMw==");
        accessMap.put(user1AccesstoApp1.getAppName(), user1AccesstoApp1);

        System.out.println(accessMap.toString());
    }


    /**private List<Access> findUserAccessListByID(String myUID){
        populateData();
        /// find User in the role list
        ArrayList<Access> currentUserList = new ArrayList<Access>();
        for (Iterator<Access> i = accessList.iterator(); i.hasNext(); ) {
            //System.out.println(i.next());
            Access currentItem = (Access)i.next();
            if(currentItem.getUserId().equals(myUID)){
                currentUserList.add(currentItem);
            }
        }
        return currentUserList;
    }**/

    public ResponseEntity<String> isAdmin(String userId){
        //populateData();
        System.out.println("1: "+userId);
        System.out.println("2: "+userMap.get(userId));

        if(userMap.get(userId).isUserAdmin()){
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<String> addAppToAccessTable(String appId,String appName, String apiKey,String userId) {
        System.out.println("Inside addAppToAccessTable");
        if (validateStringInput(appId) && validateStringInput(appName) && validateStringInput(apiKey)) {
            try {
                System.out.println("Inside try");
                User user = userMap.get(userId);
                Access newAppaccess = new Access(appId, appName, user, apiKey);
                accessMap.put(newAppaccess.getAppName(), newAppaccess);

                System.out.println(accessMap.toString());
                return  new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception ex) {
                ex.printStackTrace();
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else{
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<String> validateUserAndToken(String userId, String appName, String apiKey) {
       // populateData();
        System.out.println("userId: " + userId + " appName: " +appName + " apiKey: "+ apiKey);
        if(accessMap.get(appName).doesUserHasAccessToApp(appName,userId,apiKey)){
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
        /*
        List<Access> userAccessList = findUserAccessListByID(userId);
        boolean isvalidUser = false;

        Access userAccess =new Access();
        if(validateStringInput(userId) && validateStringInput(appName)){

            //userAccess= userAccessList.stream().filter(access -> access.getRole().equals("Admin")).findAny().orElse(null);
            userAccessList.forEach(access -> {
                if( access.getAppName().equalsIgnoreCase(appName) ){
                        //return new ResponseEntity<>(HttpStatus.OK);
                        userAccess.setUserId(access.getRole());
                        userAccess.setAppName(access.getAppName());
                        userAccess.setRole(access.getRole());

                     }
                });

                System.out.println(userAccess);
            }else{
                //isadmin = false;
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

        if(userAccess.getUserId() != null){
            // return new ResponseEntity(HttpStatus.FORBIDDEN);
            isvalidUser = true;
        }else{
            System.out.println(userId);
            isvalidUser = false;
        }
        if(isvalidUser){
            return new ResponseEntity<String>(HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }*/

    }
    private boolean validateStringInput(String input){

        if(input != "" && input != null){
            return true;
        }else{
            return false;
        }
    }


}








