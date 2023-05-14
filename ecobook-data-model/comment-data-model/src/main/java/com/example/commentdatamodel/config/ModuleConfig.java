package com.example.commentdatamodel.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example.commentdatamodel")
@EntityScan("com.example.commentdatamodel.entity")
public class ModuleConfig {
}