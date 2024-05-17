package org.example.strategy;

import org.example.bom.Deal;
import org.example.bom.PaymentType;

public class CashSalesStrategy implements SalesStrategy {

    @Override
    public void sell(Deal deal) {
        Float totalPrice = deal.getVehicle().getPrice() * (1 - ((float) deal.getClient().getDiscount() / 100));

        deal.setTotalPrice(totalPrice);
        deal.setPaymentType(PaymentType.CASH);
        deal.setMonthlyPayment(0F);
        deal.setPaid(totalPrice);
    }
}
