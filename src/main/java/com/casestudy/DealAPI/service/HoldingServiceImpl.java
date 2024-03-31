package com.casestudy.DealAPI.service;

import com.casestudy.DealAPI.error.MemberNotFoundExcepion;
import com.casestudy.DealAPI.repository.HoldingRepository;
import com.casestudy.model.Holding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HoldingServiceImpl implements HoldingService {

    @Autowired
    HoldingRepository holdingRepository;

    @Override
    public List<Holding> getMemberHoldings(String memberCode) {
        //validate member
        validate(memberCode);

        // retrieve holdings
        return holdingRepository.findByMemberCode(memberCode);
    }

    @Override
    public List<Holding> getMemberHoldingsForFund(String memberCode, String fundCode) {
        return holdingRepository.findByMemberCodeAndFundCode(memberCode, fundCode);
    }

    private void validate(String memberCode) {
        if (!memberCode.matches("[A-Za-z0-9]+")) {
            String message = "Member not found";
            log.error(message);
            throw new MemberNotFoundExcepion(message);
        }
    }
}
