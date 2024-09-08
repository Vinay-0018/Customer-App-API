package com.axis.bank.services;

import com.axis.bank.dto.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {

    void sendEmail(EmailDetails emailDetails);
}