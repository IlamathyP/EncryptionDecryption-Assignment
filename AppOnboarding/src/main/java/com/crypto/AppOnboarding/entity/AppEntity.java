package com.crypto.AppOnboarding.entity;


import jakarta.persistence.*;
import lombok.*;




@Entity
@Table(name = "AppInfo_TBL")
@NoArgsConstructor
@AllArgsConstructor
public class AppEntity {
    @Id
   // @GeneratedValue
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String appName;

    private String apiKey;

    public int getId() {
        return id;
    }

    public String getAppName() {
        return appName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
