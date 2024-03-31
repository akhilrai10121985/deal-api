package com.casestudy.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "holding")
@Data
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "member_code")
    private String memberCode;

    @Column(name = "fund_code")
    private String fundCode;

    @Column
    private double units;

}
