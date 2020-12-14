package com;

import com.alibaba.fastjson.JSON;
import com.domain.Employee;
import com.mapper.IEmployeeDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@SpringBootTest
class SpringbootCacheApplicationTests {

    @Autowired
    IEmployeeDao employeeDao;

    @Autowired
    StringRedisTemplate stringRedisTemplate;//k-v均为string

    @Autowired
    RedisTemplate redisTemplate;//k-v均为object

    @Test
    void contextLoads() {
        Employee employee = employeeDao.findById(1);
        System.out.println(employee);
    }

    @Test
    void test1(){
//        stringRedisTemplate.opsForValue().append("msg","world");
//        String msg = stringRedisTemplate.opsForValue().get("msg");
//        System.out.println(msg);
        stringRedisTemplate.opsForList().leftPush("List","value1");
        stringRedisTemplate.opsForList().leftPush("List","value2");
        stringRedisTemplate.opsForList().leftPush("List","value3");
        List<String> list = stringRedisTemplate.opsForList().range("List", 0, -1);
        System.out.println(list);
    }

    @Test
    void test2(){
        Employee employee = employeeDao.findById(1);
        //默认使用byte[]保存数据
//        redisTemplate.opsForValue().set("emp1",employee);
        String emp = JSON.toJSONString(employee);
        stringRedisTemplate.opsForValue().set("emp1",emp);
    }

}
