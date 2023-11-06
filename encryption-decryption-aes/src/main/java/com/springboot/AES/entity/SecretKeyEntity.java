package com.springboot.AES.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.crypto.SecretKey;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Key_TBL")
public class SecretKeyEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int Id;

    @Column(unique=true)
    private String secretKeyName;
    private String secretKeyValue;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getSecretKeyName() {
        return secretKeyName;
    }

    public void setSecretKeyName(String secretKeyName) {
        this.secretKeyName = secretKeyName;
    }

    public String getSecretKeyValue() {
        return secretKeyValue;
    }

    public void setSecretKeyValue(String secretKeyValue) {
        this.secretKeyValue = secretKeyValue;
    }
}
