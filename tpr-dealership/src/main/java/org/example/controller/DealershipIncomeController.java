package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.web.response.IncomeResponse;
import org.example.service.DealershipIncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/income")
public class DealershipIncomeController {

    private final DealershipIncomeService dealershipIncomeService;

    @GetMapping("/year/{year}")
    public ResponseEntity<IncomeResponse> getByYear(@PathVariable("year") Integer year) {
        return ResponseEntity.status(HttpStatus.OK).body(dealershipIncomeService.incomeByYear(year));
    }
}
