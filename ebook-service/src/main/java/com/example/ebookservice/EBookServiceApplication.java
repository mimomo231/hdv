package com.example.ebookservice;

import com.example.ebookdatamodel.config.EnableEBookDataModel;
import com.example.ebookdatamodel.config.liquibase.EnableEBookLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableEBookDataModel
@EnableEBookLiquibase
@SpringBootApplication
public class EBookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EBookServiceApplication.class, args);
	}

}
