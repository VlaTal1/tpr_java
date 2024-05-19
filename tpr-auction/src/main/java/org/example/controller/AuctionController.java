package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.exception.AuctionNotFoundException;
import org.example.service.AuctionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/{auctionId}/start")
    public void startAuction(@PathVariable Long auctionId) throws AuctionNotFoundException {
        auctionService.startAuction(auctionId);
    }
}
