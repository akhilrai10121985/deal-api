package com.casestudy.DealAPI.service;

import com.casestudy.model.Buy;
import com.casestudy.model.Sell;
import com.casestudy.model.Switch;

public interface ProcessOrderVisitor {

    void visit(Buy buyDeal);

    void visit(Sell sellDeal);

    void visit(Switch switchDeal);
}
