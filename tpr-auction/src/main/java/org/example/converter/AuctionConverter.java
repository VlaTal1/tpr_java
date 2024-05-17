package org.example.converter;

import org.example.bom.Auction;
import org.example.bom.AuctionStatus;
import org.example.dto.db.AuctionDTO;
import org.springframework.stereotype.Component;

@Component
public class AuctionConverter implements Converter<AuctionDTO, Auction> {

    @Override
    public Auction fromDTO(AuctionDTO dto) {
        return Auction.builder()
                .id(dto.getId())
                .vehicleId(dto.getVehicleId())
                .startTime(dto.getStartTime())
                .bidTimeoutSec(dto.getBidTimeoutSec())
                .startPrice(dto.getStartPrice())
                .minBid(dto.getMinBid())
                .auctionStatus(AuctionStatus.valueOf(dto.getAuctionStatus()))
                .build();
    }

    @Override
    public AuctionDTO toDTO(Auction bom) {
        return AuctionDTO.builder()
                .id(bom.getId())
                .vehicleId(bom.getVehicleId())
                .startTime(bom.getStartTime())
                .bidTimeoutSec(bom.getBidTimeoutSec())
                .startPrice(bom.getStartPrice())
                .minBid(bom.getMinBid())
                .auctionStatus(bom.getAuctionStatus().toString())
                .build();
    }
}
