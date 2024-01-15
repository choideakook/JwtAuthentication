package com.atowz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AtowzApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtowzApplication.class, args);
    }

}
