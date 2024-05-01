package org.example.strategy;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.example.bom.Deal;

@AllArgsConstructor
@Setter
public class SalesContext {

    private SalesStrategy salesStrategy;

    public void executeSales(Deal deal) {
        salesStrategy.sell(deal);
    }
}
