package com.example.cartservice;

import com.example.cartdatamodel.config.EnableCartDataModel;
import com.example.cartdatamodel.config.liquibase.EnableCartLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableCartLiquibase
@EnableCartDataModel
@SpringBootApplication
public class CartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartServiceApplication.class, args);
	}

}
