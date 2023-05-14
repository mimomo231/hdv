package com.example.ecobookconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class EcobookConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcobookConfigApplication.class, args);
    }

}
