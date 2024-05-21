package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.AuctionIncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/income")
@RequiredArgsConstructor
public class AuctionIncomeController {

    private final AuctionIncomeService auctionIncomeService;

    @GetMapping("/year/{year}")
    public ResponseEntity<Float> get(@PathVariable("year") Integer year) {
        return ResponseEntity.status(HttpStatus.OK).body(auctionIncomeService.incomeByYear(year));
    }
}
