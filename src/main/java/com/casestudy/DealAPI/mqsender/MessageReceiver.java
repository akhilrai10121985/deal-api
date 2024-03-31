package com.casestudy.DealAPI.mqsender;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class MessageReceiver {

    //@JmsListener(destination = "order")
    public void receiveMessage(String msg) {
        log.info("Received");
        //log.info("Received " + msg );
    }
}
