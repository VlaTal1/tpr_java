package org.example.strategy;

import org.example.bom.Deal;
import org.example.bom.PaymentType;

public class CreditSalesStrategy implements SalesStrategy {

    @Override
    public void sell(Deal deal) {
        int monthlyPayment = deal.getVehicle().calculateCredit();

        deal.setTotalPrice(deal.getVehicle().getPrice());
        deal.setPaymentType(PaymentType.CREDIT);
        deal.setMonthlyPayment((float) monthlyPayment);
        deal.setPaid(0F);
    }
}
