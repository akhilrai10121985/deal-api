package com.casestudy.DealAPI.service;

import com.casestudy.DealAPI.error.NotEnoughHoldingsExcepion;
import com.casestudy.DealAPI.repository.HoldingRepository;
import com.casestudy.DealAPI.repository.OrderRepository;
import com.casestudy.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProcessOrderVisitorImplTest {

    @InjectMocks
    ProcessOrderVisitorImpl processOrderVisitor;

    @Mock
    HoldingServiceImpl holdingService;
    @Mock
    private HoldingRepository holdingRepository;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void testValidBuyDealNewHolding() {
        Buy buy = new Buy();
        buy.setMemberCode("m1");
        buy.setInFund("ICICI");
        buy.setUnits(10);
        when(holdingService.getMemberHoldingsForFund(anyString(), anyString())).thenReturn(new ArrayList<>());
        processOrderVisitor.visit(buy);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(holdingRepository, times(1)).save(any(Holding.class));
    }

    @Test
    void testValidBuyDealExistingHolding() {
        Buy buy = new Buy();
        buy.setMemberCode("m1");
        buy.setInFund("ICICI");
        buy.setUnits(10);
        Holding holding = new Holding();
        holding.setMemberCode("m1");
        holding.setFundCode("ICICI");
        holding.setUnits(10);
        List<Holding> holdings = new ArrayList<>();
        holdings.add(holding);
        when(holdingService.getMemberHoldingsForFund(buy.getMemberCode(), buy.getInFund())).thenReturn(holdings);
        processOrderVisitor.visit(buy);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(holdingRepository, times(1)).save(any(Holding.class));
    }

    @Test
    void testValidSellDeal() {
        Sell sell = new Sell();
        sell.setMemberCode("m1");
        sell.setOutFund("ICICI");
        sell.setUnits(10);
        Holding holding = new Holding();
        holding.setMemberCode("m1");
        holding.setFundCode("ICICI");
        holding.setUnits(10);
        List<Holding> holdings = new ArrayList<>();
        holdings.add(holding);
        when(holdingService.getMemberHoldingsForFund(sell.getMemberCode(), sell.getOutFund())).thenReturn(holdings);
        processOrderVisitor.visit(sell);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(holdingRepository, times(1)).save(any(Holding.class));
    }

    @Test
    void testSellDealNoHoldings() {

        Sell sell = new Sell();
        sell.setMemberCode("m1");
        sell.setOutFund("ICICI");
        sell.setUnits(10);
        when(holdingService.getMemberHoldingsForFund(sell.getMemberCode(), sell.getOutFund())).thenReturn(null);
        RuntimeException runtimeException = assertThrows(NotEnoughHoldingsExcepion.class, () -> {
                    processOrderVisitor.visit(sell);
                }
        );
        String expectedMessage = sell.getMemberCode() + "doesn't have any holding against fund " + sell.getOutFund();
        String actualMessage = runtimeException.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testSellDealNotEnoughHoldings() {
        Sell sell = new Sell();
        sell.setMemberCode("m1");
        sell.setOutFund("ICICI");
        sell.setUnits(15);
        Holding holding = new Holding();
        holding.setMemberCode("m1");
        holding.setFundCode("ICICI");
        holding.setUnits(10);
        List<Holding> holdings = new ArrayList<>();
        holdings.add(holding);
        when(holdingService.getMemberHoldingsForFund(sell.getMemberCode(), sell.getOutFund())).thenReturn(holdings);
        RuntimeException runtimeException = assertThrows(NotEnoughHoldingsExcepion.class, () -> {
                    processOrderVisitor.visit(sell);
                }
        );
        String expectedMessage = sell.getMemberCode() + "doesn't have enough holdings against fund " + sell.getOutFund();
        String actualMessage = runtimeException.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testValidSwitchDealNewHolding() {
        Switch switchDeal = new Switch();
        switchDeal.setMemberCode("m1");
        switchDeal.setOutFund("ICICI");
        switchDeal.setInFund("HDFC");
        switchDeal.setUnits(5);
        Holding holding = new Holding();
        holding.setMemberCode("m1");
        holding.setFundCode("ICICI");
        holding.setUnits(10);
        List<Holding> holdings = new ArrayList<>();
        holdings.add(holding);
        when(holdingService.getMemberHoldingsForFund(switchDeal.getMemberCode(), switchDeal.getOutFund())).thenReturn(holdings);
        processOrderVisitor.visit(switchDeal);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(holdingRepository, times(2)).save(any(Holding.class));

    }

    @Test
    void testValidSwitchDealExistingHolding() {
        Switch switchDeal = new Switch();
        switchDeal.setMemberCode("m1");
        switchDeal.setOutFund("ICICI");
        switchDeal.setInFund("HDFC");
        switchDeal.setUnits(5);

        Holding holding = new Holding();
        holding.setMemberCode("m1");
        holding.setFundCode("ICICI");
        holding.setUnits(10);
        List<Holding> holdings = new ArrayList<>();
        holdings.add(holding);
        when(holdingService.getMemberHoldingsForFund(switchDeal.getMemberCode(), switchDeal.getOutFund())).thenReturn(holdings);

        Holding existingBuyFundHolding = new Holding();
        existingBuyFundHolding.setMemberCode("m1");
        existingBuyFundHolding.setFundCode("ICICI");
        existingBuyFundHolding.setUnits(10);
        List<Holding> existingBuyFundHoldings = new ArrayList<>();
        existingBuyFundHoldings.add(existingBuyFundHolding);
        when(holdingService.getMemberHoldingsForFund(switchDeal.getMemberCode(), switchDeal.getInFund())).thenReturn(existingBuyFundHoldings);

        processOrderVisitor.visit(switchDeal);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(holdingRepository, times(2)).save(any(Holding.class));
    }

    @Test
    void testSwitchDealNoHolding() {
        Switch switchDeal = new Switch();
        switchDeal.setMemberCode("m1");
        switchDeal.setOutFund("ICICI");
        switchDeal.setInFund("HDFC");
        switchDeal.setUnits(15);
        when(holdingService.getMemberHoldingsForFund(switchDeal.getMemberCode(), switchDeal.getOutFund())).thenReturn(null);
        RuntimeException runtimeException = assertThrows(NotEnoughHoldingsExcepion.class, () -> {
                    processOrderVisitor.visit(switchDeal);
                }
        );
        String expectedMessage = switchDeal.getMemberCode() + "doesn't have any holding against fund " + switchDeal.getOutFund();
        String actualMessage = runtimeException.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testSwitchDealNotEnoughHolding() {
        Switch switchDeal = new Switch();
        switchDeal.setMemberCode("m1");
        switchDeal.setOutFund("ICICI");
        switchDeal.setInFund("HDFC");
        switchDeal.setUnits(15);
        Holding holding = new Holding();
        holding.setMemberCode("m1");
        holding.setFundCode("ICICI");
        holding.setUnits(10);
        List<Holding> holdings = new ArrayList<>();
        holdings.add(holding);
        when(holdingService.getMemberHoldingsForFund(switchDeal.getMemberCode(), switchDeal.getOutFund())).thenReturn(holdings);
        RuntimeException runtimeException = assertThrows(NotEnoughHoldingsExcepion.class, () -> {
                    processOrderVisitor.visit(switchDeal);
                }
        );
        String expectedMessage = switchDeal.getMemberCode() + "doesn't have enough holdings against fund " + switchDeal.getOutFund();
        String actualMessage = runtimeException.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }


}