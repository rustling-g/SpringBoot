package com;

import com.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SpringbootAmqpApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    void contextLoads() {
        Map<String, Object> map = new HashMap<>();
        map.put("user", Arrays.asList("2",5,true));
        map.put("msg","第二个消息");
        map.put("test",55);
        rabbitTemplate.convertAndSend("mydirect","atguigu",map);
    }

    @Test
    public void receive(){
        Object o = rabbitTemplate.receiveAndConvert("atguigu");
        System.out.println(o.getClass());
        System.out.println(o);
    }

    @Test
    public void testBook(){
        Book book = new Book("西游记", "吴承恩");
        rabbitTemplate.convertAndSend("myfanout","book",book);

//        Object o1 = rabbitTemplate.receiveAndConvert("atguigu");
//        System.out.println(o1.getClass());
//        System.out.println(o1);
//        System.out.println("-------------------------");
//        Object o2 = rabbitTemplate.receiveAndConvert("guli.news");
//        System.out.println(o2.getClass());
//        System.out.println(o2);
    }

    @Test
    public void createAMQP(){
        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.newDirect"));
        System.out.println("创建完成");
        amqpAdmin.declareQueue(new Queue("amqp.newQueue"));
        System.out.println("创建完成");
        amqpAdmin.declareBinding(new Binding("amqp.newQueue", Binding.DestinationType.QUEUE,
                "amqpAdmin.newDirect","book",null));
    }

}
