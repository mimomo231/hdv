package com.example.paymentservice;

import com.example.paymentdatamodel.config.EnablePaymentDataModel;
import com.example.paymentdatamodel.config.liquibase.EnablePaymentLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnablePaymentLiquibase
@EnablePaymentDataModel
@SpringBootApplication
public class PaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServiceApplication.class, args);
	}

}
