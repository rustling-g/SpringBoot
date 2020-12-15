package com.provider.controller;

import com.provider.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gg
 * @create 2020-12-15 上午10:01
 */
@RestController
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/ticket")
    public String getTicket(){
        String ticket = ticketService.getTicket();
        return ticket;
    }
}
