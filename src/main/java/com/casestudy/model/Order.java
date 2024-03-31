package com.casestudy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "deal_type")
    private DealType dealType;

    @Column(name = "member_code")
    private String memberCode;

    @Column(name = "in_fund")
    private String inFund;

    @Column(name = "out_fund")
    private String outFund;

    @Column
    private String status;

    @Column(name = "units")
    private double units;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
