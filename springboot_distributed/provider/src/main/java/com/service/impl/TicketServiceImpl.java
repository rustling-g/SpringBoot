package com.service.impl;

import com.service.TicketService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author gg
 * @create 2020-12-14 下午11:05
 */
@Component
@Service
public class TicketServiceImpl implements TicketService {
    @Override
    public String getTicket() {
        return "<我和我的祖国>";
    }
}
