package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.web.request.BidRequest;
import org.example.exception.AuctionEndedException;
import org.example.exception.AuctionNotFoundException;
import org.example.exception.ClientNotFoundException;
import org.example.service.BidService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/bid")
@RequiredArgsConstructor
public class BidController {

    private final BidService bidService;

    @PostMapping("/auction/{auctionId}")
    public void placeBid(@PathVariable Long auctionId, @RequestBody BidRequest bidRequest) throws ClientNotFoundException, AuctionEndedException, AuctionNotFoundException {
        bidService.placeBid(auctionId, bidRequest);
    }
}
