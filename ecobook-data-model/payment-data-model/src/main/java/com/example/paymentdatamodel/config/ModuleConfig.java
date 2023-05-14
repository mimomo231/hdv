package com.example.paymentdatamodel.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.paymentdatamodel")
@EntityScan("com.example.paymentdatamodel.entity")
public class ModuleConfig {
}