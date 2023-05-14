package com.example.commentservice;

import com.example.commentdatamodel.config.EnableCommentDataModel;
import com.example.commentdatamodel.config.liquibase.EnableCommentLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableCommentDataModel
@EnableCommentLiquibase
@SpringBootApplication
public class CommentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentServiceApplication.class, args);
	}

}
