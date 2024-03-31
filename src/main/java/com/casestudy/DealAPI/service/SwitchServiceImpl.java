package com.casestudy.DealAPI.service;

import com.casestudy.model.SwitchRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SwitchServiceImpl implements SwitchService {
    @Override
    public void processSwitch(SwitchRequest switchRequest) {
        log.info("processing switch {}", switchRequest);

        // populate SwitchOrder table

        // process switch
    }

    private void populateSwitchOrder(SwitchRequest switchRequest) {

    }

    private void processSwitch() {
        // retrieve existing holdings
    }

    private void buy() {
        // validate enough holdings
        // reduce unit holdings from outFund
        // increase unit holdings in inFund
    }
}
