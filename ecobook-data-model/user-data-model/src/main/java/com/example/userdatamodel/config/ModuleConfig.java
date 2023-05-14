package com.example.userdatamodel.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.userdatamodel")
@EntityScan("com.example.userdatamodel.entity")
public class ModuleConfig {
}