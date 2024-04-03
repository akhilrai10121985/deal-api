package com.casestudy.DealAPI.listener;

import com.casestudy.DealAPI.service.ProcessOrderVisitor;
import com.casestudy.model.Deal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DealOrderListener {

    @Autowired
    ProcessOrderVisitor processOrderVisitor;

    @JmsListener(destination = "${deal.queue}", containerFactory = "myFactory")
    public void receiveMessage(Deal deal) {
        log.info("Received deal message {}", deal);
        deal.accept(processOrderVisitor);
        log.info("Message processed ok");
    }
}
