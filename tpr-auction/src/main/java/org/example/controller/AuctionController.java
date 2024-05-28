package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.exception.AuctionNotFoundException;
import org.example.exception.BadRequestException;
import org.example.exception.VehicleNotFoundException;
import org.example.exception.VehicleNotUsedException;
import org.example.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auctions")
@RequiredArgsConstructor
public class AuctionController {

    private final AuctionService auctionService;

    @PostMapping("/{auctionId}/start")
    public void startAuction(@PathVariable Long auctionId) throws AuctionNotFoundException {
        auctionService.startAuction(auctionId);
    }

    @PostMapping("/")
    public ResponseEntity<Auction> create(@RequestBody Auction auction) throws VehicleNotFoundException, VehicleNotUsedException, BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(auctionService.create(auction));
    }
}
