package com.casestudy.DealAPI.repository;

import com.casestudy.model.Holding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HoldingRepository extends JpaRepository<Holding, String> {

    List<Holding> findByMemberCode(String memberCode);

    List<Holding> findByMemberCodeAndFundCode(String memberCode, String fundCode);

}
