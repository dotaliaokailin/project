package com.liao.server;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Reference
    private TicketService ticketService;
    public void getTicket(){
        String ticket = ticketService.getTicket();
        System.out.println(ticket);
    }
}
