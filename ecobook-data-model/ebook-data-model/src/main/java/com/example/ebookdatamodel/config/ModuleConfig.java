package com.example.ebookdatamodel.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.ebookdatamodel")
@EntityScan("com.example.ebookdatamodel.entity")
public class ModuleConfig {
}