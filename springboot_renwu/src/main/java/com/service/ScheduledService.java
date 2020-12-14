package com.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author gg
 * @create 2020-12-14 下午1:28
 */
@Service
public class ScheduledService {
    @Scheduled(cron = "0 * * * * MON-FRI")
    public void hello(){
        System.out.println("hello");
    }
}
