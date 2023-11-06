package com.springboot.AES.model;


import jdk.jfr.DataAmount;
import lombok.*;

import javax.crypto.SecretKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class SecretKeyModel {
    private String secretKeyName;
    private String secretKeyValue;


}
