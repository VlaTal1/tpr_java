package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Deal;
import org.example.dto.web.DealRequest;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleOutOfStockException;
import org.example.service.SalesServiceFacade.SalesServiceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deal")
public class DealController {

    private final SalesServiceFacade salesServiceFacade;

    @PostMapping("/")
    public ResponseEntity<Deal> add(
            @RequestBody DealRequest dealRequest) throws NotFoundException, VehicleOutOfStockException {
        Deal deal = salesServiceFacade.createDeal(dealRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(deal);
    }
}
