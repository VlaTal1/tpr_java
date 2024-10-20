package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.exception.*;
import org.example.service.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/")
    public ResponseEntity<List<Auction>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(auctionService.getAll());
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<Auction> getById(@PathVariable Long auctionId) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(auctionService.getById(auctionId));
    }
}
