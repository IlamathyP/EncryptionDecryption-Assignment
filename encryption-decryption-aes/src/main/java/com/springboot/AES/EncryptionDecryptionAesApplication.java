package com.springboot.AES;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication
public class EncryptionDecryptionAesApplication {

	/**@Autowired
	private AppInfoRepository appInfoRepository;

	@PostConstruct
	public void initAppInfo(){
		List<AppEntity> appEntityList = Stream.of(
				new AppEntity(1,"App1","apikey1"),
				new AppEntity(2,"App2","apikey2"),
				new AppEntity(3,"App3","apikey3"),
				new AppEntity(4,"App4","apikey4")
		).collect(Collectors.toList());
		appInfoRepository.saveAll(appEntityList);
	}**/


	public static void main(String[] args) {

		SpringApplication.run(EncryptionDecryptionAesApplication.class, args);
	}

}
