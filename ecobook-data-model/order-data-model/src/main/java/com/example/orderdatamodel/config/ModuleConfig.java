package com.example.orderdatamodel.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.orderdatamodel")
@EntityScan("com.example.orderdatamodel.entity")
public class ModuleConfig {
}