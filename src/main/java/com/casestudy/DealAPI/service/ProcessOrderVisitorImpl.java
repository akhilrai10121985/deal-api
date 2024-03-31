package com.casestudy.DealAPI.service;

import com.casestudy.DealAPI.error.NotEnoughHoldingsExcepion;
import com.casestudy.DealAPI.repository.HoldingRepository;
import com.casestudy.DealAPI.repository.OrderRepository;
import com.casestudy.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ProcessOrderVisitorImpl implements ProcessOrderVisitor {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    HoldingService holdingService;

    @Autowired
    HoldingRepository holdingRepository;

    @Override
    public void visit(Buy buyDeal) {
        log.info("Process buy order");
        // create entry in order table
        populateOrder(buyDeal.getMemberCode(), DealType.BUY, buyDeal.getUnits(), buyDeal.getInFund(), null);
        // increase units in InFund
        increaseHoldings(buyDeal.getMemberCode(), buyDeal.getUnits(), buyDeal.getInFund());
    }

    @Override
    public void visit(Sell sellDeal) {
        log.info("Process sell order");
        // create entry in order table
        populateOrder(sellDeal.getMemberCode(), DealType.SELL, sellDeal.getUnits(), null, sellDeal.getOutFund());

        // reduce holdings in outFund
        reduceHoldings(sellDeal.getMemberCode(), sellDeal.getUnits(), sellDeal.getOutFund());
    }

    @Override
    public void visit(Switch switchDeal) {
        log.info("Process switch order");
        // populate order in order table
        populateOrder(switchDeal.getMemberCode(), DealType.SWITCH, switchDeal.getUnits(), switchDeal.getInFund(), switchDeal.getOutFund());

        // reduce holdings in outFund
        reduceHoldings(switchDeal.getMemberCode(), switchDeal.getUnits(), switchDeal.getOutFund());

        // increase units in InFund
        increaseHoldings(switchDeal.getMemberCode(), switchDeal.getUnits(), switchDeal.getInFund());

    }

    private void increaseHoldings(String memberCode, double units, String inFund) {
        List<Holding> inFundHoldings = holdingService.getMemberHoldingsForFund(memberCode, inFund);
        Holding inFundholding = new Holding();
        // check if member has holdings in the inFund in the holding table
        // if no, create new entry
        if (inFundHoldings == null || inFundHoldings.isEmpty()) {
            inFundholding.setMemberCode(memberCode);
            inFundholding.setFundCode(inFund);
            inFundholding.setUnits(units);
        }
        // else, increase unit holdings
        else {
            inFundholding = inFundHoldings.get(0);
            inFundholding.setUnits(increaseHoldings(inFundholding.getUnits(), units));
        }
        holdingRepository.save(inFundholding);
    }

    private void reduceHoldings(String memberCode, double units, String outFund) {
        // retrieve member holdings
        List<Holding> holdings = holdingService.getMemberHoldingsForFund(memberCode, outFund);
        Holding holding = new Holding();
        // if no holding, throw exception
        if (holdings == null || holdings.isEmpty()) {
            String message = memberCode + "doesn't have any holding against fund " + outFund;
            throw new NotEnoughHoldingsExcepion(message);
        }
        // else, reduce unit holdings
        else {
            holding = holdings.get(0);
            double existingUnits = holding.getUnits();
            // validate enough holdings to place buy deal
            if (existingUnits < units) {
                String message = memberCode + "doesn't have enough holdings against fund " + outFund;
                throw new NotEnoughHoldingsExcepion(message);
            } else {
                // reduce holdings
                double newUnits = reduceHoldings(existingUnits, units);
                holding.setUnits(newUnits);
                holdingRepository.save(holding);
            }
        }
    }

    private double reduceHoldings(double existingHoldings, double newHoldings) {

        BigDecimal b1 = new BigDecimal(Double.toString(existingHoldings));
        BigDecimal b2 = new BigDecimal(Double.toString(newHoldings));
        return b1.subtract(b2).doubleValue();
    }

    private double increaseHoldings(double existingHoldings, double newHoldings) {

        BigDecimal b1 = new BigDecimal(Double.toString(existingHoldings));
        BigDecimal b2 = new BigDecimal(Double.toString(newHoldings));
        return b1.add(b2).doubleValue();
    }

    private void populateOrder(String memberCode, DealType dealType, double units, String inFund, String outFund) {
        Order order = new Order();
        order.setMemberCode(memberCode);
        order.setDealType(dealType);
        order.setUnits(units);
        order.setInFund(inFund);
        order.setOutFund(outFund);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setStatus(Status.NEW.name());
        order.setCreatedBy("deal-api");
        order.setUpdatedBy("deal-api");
        orderRepository.save(order);
    }
}
