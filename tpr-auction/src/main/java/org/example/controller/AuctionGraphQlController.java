package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Auction;
import org.example.dto.web.graphQl.AuctionInput;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleNotFoundException;
import org.example.exception.VehicleNotUsedException;
import org.example.service.AuctionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
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

    @MutationMapping
    Auction addAuction(@Argument AuctionInput auction) throws VehicleNotFoundException, VehicleNotUsedException, BadRequestException {
        return auctionService.create(auction);
    }
}
