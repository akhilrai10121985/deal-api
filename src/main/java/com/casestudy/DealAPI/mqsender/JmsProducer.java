package com.casestudy.DealAPI.mqsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JmsProducer {

    @Autowired
    JmsTemplate jmsTemplate;

    @Value("${active-mq.topic}")
    private String topic;

    public void sendMessage(String message) {
        try {
            log.info("Attempting Send message to Topic: " + topic);
            jmsTemplate.convertAndSend(topic, message);
            log.info("sent");
        } catch (Exception e) {
            log.error("Recieved Exception during send Message: ", e);
        }
    }
}
