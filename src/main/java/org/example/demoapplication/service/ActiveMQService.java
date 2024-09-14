package org.example.demoapplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ActiveMQService {
    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendWithdrawalNotification(String firstName , String lastName , BigDecimal withdrawalAmount) {
        // Message body
        String message = "Dear customer " + firstName + " " + lastName + ", an amount of " + withdrawalAmount + " has been debited from your deposit.";

        // Send the message to ActiveMQ
        jmsTemplate.convertAndSend("smsQueue", message);
    }

}
