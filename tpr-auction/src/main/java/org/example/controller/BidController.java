package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Bid;
import org.example.dto.web.request.BidRequest;
import org.example.exception.*;
import org.example.service.BidService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bid")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("/auction/{auctionId}")
    public void placeBid(@PathVariable Long auctionId, @RequestBody BidRequest bidRequest) throws ClientNotFoundException, AuctionEndedException, AuctionNotFoundException, BadRequestException {
        bidService.placeBid(auctionId, bidRequest);
    }

    @GetMapping("/auction/{auctionId}/lastBid")
    public ResponseEntity<Bid> getLastBidByAuctionId(@PathVariable Long auctionId) throws NotFoundException, AuctionNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(bidService.getLastBidByAuctionId(auctionId));
    }
}
