package com.casestudy.model;

import com.casestudy.DealAPI.service.DealElement;
import com.casestudy.DealAPI.service.ProcessOrderVisitor;
import lombok.Data;

@Data
public class Sell extends Deal implements DealElement {

    private String OutFund;

    @Override
    public void accept(ProcessOrderVisitor processOrderVisitor) {
        processOrderVisitor.visit(this);
    }
}
