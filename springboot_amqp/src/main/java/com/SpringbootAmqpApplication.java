package com;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit       //开启基于注解的rabbitmq
public class SpringbootAmqpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootAmqpApplication.class, args);
    }

}
