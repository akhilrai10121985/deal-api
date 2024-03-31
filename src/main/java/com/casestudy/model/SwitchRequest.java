package com.casestudy.model;

import lombok.Data;

@Data
public class SwitchRequest {

    private String memberCode;

    private String InFund;

    private String OutFund;

    private double units;
}
