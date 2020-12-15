package com.provider.service;

import org.springframework.stereotype.Service;

/**
 * @author gg
 * @create 2020-12-15 上午10:00
 */
@Service
public class TicketService {
    public String getTicket(){
        System.out.println("8002");
        return "<你好>";
    }
}
