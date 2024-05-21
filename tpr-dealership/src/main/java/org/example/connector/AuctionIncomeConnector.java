package org.example.connector;

import org.example.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${auction-url}", name = "auctionIncomeConnector", configuration = FeignConfig.class)
public interface AuctionIncomeConnector {

    @GetMapping("income/year/{year}")
    Float getByYear(@PathVariable("year") Integer year);
}
