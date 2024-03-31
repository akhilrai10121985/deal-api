package com.casestudy.DealAPI.service;

public interface DealElement {

    public void accept(ProcessOrderVisitor processOrderVisitor);
}
