package com.eindproject.YogaShare;

import com.eindproject.YogaShare.authorities.EAuthority;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class YogaShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(YogaShareApplication.class, args);
	}

}
