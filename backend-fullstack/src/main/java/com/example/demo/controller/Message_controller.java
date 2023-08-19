package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EventData;
import com.example.demo.dao.LoginData;
import com.example.demo.dao.MessageData;
import com.example.demo.model.Eventdetail;
import com.example.demo.model.UserMessage;
import com.example.demo.model.userinfo;
import com.example.demo.services.EmailSendService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/Message")
public class Message_controller {

    @Autowired
    private MessageData messageData;

    @Autowired
    private EmailSendService emailSendService;

    @Autowired
    private LoginData loginData;

    @PostMapping("/user_message")
    public String SendMessage(@RequestBody UserMessage messageBody) {

        List<userinfo> data = loginData.findAll();
        for (userinfo entity : data) {
            if (entity.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
                emailSendService.sendEmail(entity.getEmail(), messageBody.getUserName(),
                        messageBody.getUserEmail(), messageBody.getUserMessage());
            }
        }
        // emailSendService.sendEmail("tejasKadam2907@gmail.com", "Hello", "Yo Bro");

        // emailSendService.SendMail(messageBody);
        messageData.save(messageBody);

        return "Message sent successfully";

    }
}
