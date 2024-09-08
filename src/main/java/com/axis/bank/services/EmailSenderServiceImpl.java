package com.axis.bank.services;

import com.axis.bank.dto.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmail(EmailDetails emailDetails){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("axisbankdemoemail@gmail.com");
        message.setTo(emailDetails.getRecipient());
        message.setText(emailDetails.getMsgBody());
        message.setSubject(emailDetails.getSubject());

        mailSender.send(message);

        System.out.println("Mail sent successfully.....");
    }
}