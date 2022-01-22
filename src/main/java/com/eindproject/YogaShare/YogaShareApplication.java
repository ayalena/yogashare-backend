package com.eindproject.YogaShare;

//import com.eindproject.YogaShare.documents.DocumentStorageProperties;
import com.eindproject.YogaShare.documents.DocumentStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		DocumentStorageProperties.class
})
public class YogaShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(YogaShareApplication.class, args);
	}

}
