package com.casestudy.model;

import lombok.Data;

@Data
public class DealRequest {

    private String memberCode;

    private DealType dealType;

    private String fund;

    private Direction direction;

    private double units;
}
