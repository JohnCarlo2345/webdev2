package com.johncarlo.webdev2.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Webdev2Application {
    public static void main(String[] args) {
        SpringApplication.run(Webdev2Application.class, args);
    }
}

