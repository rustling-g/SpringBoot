package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gg
 * @create 2020-12-13 下午5:30
 */
@Configuration
public class MyAMQPConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyAMQPConfig.class);

    @Bean
    public MessageConverter messageConverter(){
        LOGGER.info("自定义MessageConverter加载完毕");
        return new Jackson2JsonMessageConverter();
    }
}
