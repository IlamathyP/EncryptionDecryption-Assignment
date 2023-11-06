package com.crypto.SpringSecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;


//@NoArgsConstructor
public class Access {
    private String appID;
    private String appName;
    private User user;
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }



    public Access(String appID, String appName, User user,String apiKey) {
        this.appID = appID;
        this.appName = appName;
        this.user = user;
        this.apiKey = apiKey;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Access{" +
                "appID='" + appID + '\'' +
                ", appName='" + appName + '\'' +
                ", user=" + user +
                ", apiKey='" + apiKey + '\'' +
                '}';
    }

    public boolean doesUserHasAccessToApp(String appName, String userID, String apiKey){
        System.out.println("arg :"+apiKey+"this.api"+this.apiKey);

        if(user.isUserAdmin()){
            if (apiKey.equals(this.apiKey)) {
                return true;
            }
        }
        else if(user.getUserId().equalsIgnoreCase(this.user.getUserId())){
            if (apiKey.equals(this.apiKey)){
                if(this.appName.equalsIgnoreCase((appName))){
                    return true;
                }
            }
        }
        return false;
    }
}
