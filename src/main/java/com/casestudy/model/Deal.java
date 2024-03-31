package com.casestudy.model;

import com.casestudy.DealAPI.service.DealElement;
import com.casestudy.DealAPI.service.ProcessOrderVisitor;
import lombok.Data;

@Data
public abstract class Deal implements DealElement {

    private String memberCode;

    private double units;

    @Override
    public abstract void accept(ProcessOrderVisitor processOrderVisitor);
}
