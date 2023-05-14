package com.example.orderservice;

import com.example.orderdatamodel.config.EnableOrderDataModel;
import com.example.orderdatamodel.config.liquibase.EnableOrderLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@EnableOrderDataModel
@EnableOrderLiquibase
@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
