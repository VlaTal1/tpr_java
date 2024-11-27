package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.exception.NotFoundException;
import org.example.service.AuctionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuctionGraphQlController {
    private final AuctionService auctionService;

    @QueryMapping
    List<Auction> auctions() {
        return auctionService.getAll();
    }

    @QueryMapping
    Auction auctionById(@Argument Long id) throws NotFoundException {
        return auctionService.getById(id);
    }
}
