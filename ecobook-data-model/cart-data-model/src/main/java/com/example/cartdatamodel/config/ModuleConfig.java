package com.example.cartdatamodel.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.cartdatamodel")
@EntityScan("com.example.cartdatamodel.entity")
public class ModuleConfig {
}