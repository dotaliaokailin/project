package com.liao.service;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Reference
    private TicketService ticketService;
    public String getTicket(){
        return ticketService.getTicket();
    }
}
