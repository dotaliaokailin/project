package com.liao.controller;

import com.liao.service.TicketService;
import com.liao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketController {
    @Autowired
    private UserService userService;

    @GetMapping("getTicket")
    public String getTicket(){
        return userService.getTicket();
    }
}
