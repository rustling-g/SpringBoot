package com.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author gg
 * @create 2020-12-14 下午1:07
 */
@Service
public class AsyncService {
    @Async  //告诉spring 这是一个异步方法
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("请求处理中。。。");
    }
}
