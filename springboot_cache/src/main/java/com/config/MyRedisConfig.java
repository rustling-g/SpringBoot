package com.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.*;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

/**
 * @author gg
 * @create 2020-12-13 上午11:58
 */
@Configuration
@EnableCaching
public class MyRedisConfig extends CachingConfigurerSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRedisConfig.class);

    //缓存管理器
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                //60s缓存失效
                .entryTtl(Duration.ofSeconds(60))
                //设置key序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                //设置value序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                //不缓存null值
                .disableCachingNullValues();

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .transactionAware()
                .build();
        LOGGER.info("自定义Redis缓存管理器加载完毕");
        return redisCacheManager;
    }

    //key序列化方式
    private RedisSerializer keySerializer(){
        return new StringRedisSerializer();
    }
    //value序列化方式
    private GenericJackson2JsonRedisSerializer valueSerializer(){
        return new GenericJackson2JsonRedisSerializer();
    }

    //自定义key生成器
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                return Arrays.asList(objects).toString();
            }
        };
    }
}



