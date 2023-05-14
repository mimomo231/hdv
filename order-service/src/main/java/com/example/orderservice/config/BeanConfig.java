package com.example.orderservice.config;

import com.example.api.filter.AuthTokenFilter;
import com.example.api.jwt.AuthEntryPointJwt;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.example.proxycommon.ebook.proxy")
public class BeanConfig {

    @Bean
    public Encoder encoder() {
        return new GsonEncoder();
    }

    @Bean
    public Decoder decoder() {
        return new GsonDecoder();
    }

    @Bean
    AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    AuthEntryPointJwt authEntryPointJwt() {
        return new AuthEntryPointJwt();
    }
}
