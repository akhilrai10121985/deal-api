package com.casestudy.DealAPI.service;

import com.casestudy.model.DealRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DealServiceImpl implements DealService {
    @Override
    public void processDeal(DealRequest dealRequest) {
        log.info("processing deal {}", dealRequest);

        // populate DealOrder table

        // process deal
    }

    private void populateDealOrder(DealRequest dealRequest) {

    }

    private void processDeal() {

    }

    private void buy() {
        // validate enough holdings
        // reduce unit holdings
    }

    private void sell() {
        // increase unit holdings
    }
}
