package com.axis.bank.controllers;

import com.axis.bank.dto.EmailDetails;
import com.axis.bank.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/email")
@Controller
public class EmailController {
    @Autowired
    private EmailSenderService emailSenderService;
    @PostMapping
    public ResponseEntity<String> emailSender(@RequestBody EmailDetails emailDetails){
        emailSenderService.sendEmail(emailDetails);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
