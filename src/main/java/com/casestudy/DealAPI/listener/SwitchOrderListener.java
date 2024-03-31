package com.casestudy.DealAPI.listener;

import com.casestudy.model.SwitchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SwitchOrderListener {

    @JmsListener(destination = "${switch.queue}", containerFactory = "myFactory")
    public void receiveMessage(SwitchRequest switchRequest) {
        System.out.println("Received <" + switchRequest + ">");
    }
}
