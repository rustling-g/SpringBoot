package com.service;

import com.domain.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author gg
 * @create 2020-12-13 下午5:56
 */
@Service
public class BookService {

    @RabbitListener(queues = "atguigu")
    public void receive(Book book){
        System.out.println("收到消息：  "+book);
    }

    @RabbitListener(queues = "guli.news")
    public void receive2(Message message){
        System.out.println("消息头：  "+message.getMessageProperties());
        System.out.println("消息体：  "+message.getBody());
    }
}
