package com.crypto.AppOnboarding.model;

import lombok.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
public class AppResponse {
    private int id;
    private String appName;

    private String apiKey;
}
