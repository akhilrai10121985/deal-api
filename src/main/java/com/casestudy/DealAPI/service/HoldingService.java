package com.casestudy.DealAPI.service;


import com.casestudy.model.Holding;

import java.util.List;

public interface HoldingService {
    List<Holding> getMemberHoldings(String memberCode);

    List<Holding> getMemberHoldingsForFund(String memberCode, String fundCode);
}
