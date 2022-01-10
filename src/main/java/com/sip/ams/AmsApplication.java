package com.sip.ams;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sip.ams.controllers.ArticleController;
import com.sip.ams.controllers.ProviderController;

@SpringBootApplication
public class AmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmsApplication.class, args);
		new File(ArticleController.uploadDirectory).mkdir();
		new File(ProviderController.providersDirectory).mkdir();

	}

}
