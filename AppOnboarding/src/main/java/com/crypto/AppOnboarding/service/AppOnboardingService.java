package com.crypto.AppOnboarding.service;

import com.crypto.AppOnboarding.entity.AppEntity;
import com.crypto.AppOnboarding.model.AppResponse;
import com.crypto.AppOnboarding.repository.AppInfoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class AppOnboardingService {

    @Autowired
    private WebClient webClient;

    @Autowired
    private AppInfoRepository appInfoRepository;


    @Value("${authService.base.url}")
    private String authServiceBaseUrl;

public ResponseEntity<String> onboardApp(String userId, String AppName) throws UnsupportedEncodingException {

    String queryString = "/authorizeUser/isAdmin?userId="+userId;
   // authservicebaseurl = authServiceBaseUrl + queryString;

    System.out.println(authServiceBaseUrl);
    String response = webClient.get()
            .uri(authServiceBaseUrl + queryString)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    System.out.println(response);
    System.out.println(response.substring(1,4));

    if(response.substring(1,4).equals("200")){
        AppEntity appEntity = new AppEntity();
        appEntity.setAppName(AppName);
        String encodedString = Base64.getEncoder().encodeToString(AppName.getBytes());
        appEntity.setApiKey(encodedString);
        try {
            appInfoRepository.save(appEntity);
            addAppAccess(appEntity,userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (RuntimeException ex){
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);

        }

    }else if(response.subSequence(1,4).equals("403")){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);

}

    public List<AppResponse> getAppDetails() {

    List<AppResponse> appResponseList= new ArrayList<>();
        List<AppEntity> appEntityList = appInfoRepository.findAll();

        appEntityList.forEach((appEntity) -> {
            AppResponse appResponse = new AppResponse();
            BeanUtils.copyProperties(appEntity,appResponse);
            appResponseList.add(appResponse);
        });

        return appResponseList;



    }

    private ResponseEntity<String> addAppAccess(AppEntity appEntity,String userId){
        //appId,AppName,apiKey,userId
        try {
            String addAppqueryString = "/addApp?appId=" + appEntity.getId() + "&AppName=" + appEntity.getAppName() + "&apiKey=" + appEntity.getApiKey() + "&userId=" + userId;
            String response = webClient.post()
                    .uri(authServiceBaseUrl + addAppqueryString)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            if(response.substring(1,4).equals(200)){
                return  new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
            }

        }catch (Exception ex){
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
