package com.example.userservice;

import com.example.userdatamodel.config.EnableUserDataModel;
import com.example.userdatamodel.config.liquibase.EnableUserLiquibase;
import com.example.userdatamodel.entity.UserAccount;
import com.example.userdatamodel.entity.enumtype.AccountRoleEnum;
import com.example.userservice.repository.UserAccountRepository;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@EnableDiscoveryClient
@EnableUserDataModel
@EnableUserLiquibase
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
}
