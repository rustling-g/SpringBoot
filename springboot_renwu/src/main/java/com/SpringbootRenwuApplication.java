package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync        //开启异步注解
@EnableScheduling
public class SpringbootRenwuApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRenwuApplication.class, args);
    }

}
