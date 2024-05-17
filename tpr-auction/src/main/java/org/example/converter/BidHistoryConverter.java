package org.example.converter;

import lombok.RequiredArgsConstructor;
import org.example.bom.BidHistory;
import org.example.dto.db.BidHistoryDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidHistoryConverter implements Converter<BidHistoryDTO, BidHistory> {

    private final AuctionConverter auctionConverter;

    private final BidConverter bidConverter;

    @Override
    public BidHistory fromDTO(BidHistoryDTO dto) {
        return BidHistory.builder()
                .id(dto.getId())
                .auction(auctionConverter.fromDTO(dto.getAuction()))
                .bid(bidConverter.fromDTO(dto.getBid()))
                .time(dto.getTime())
                .build();
    }

    @Override
    public BidHistoryDTO toDTO(BidHistory bom) {
        return BidHistoryDTO.builder()
                .id(bom.getId())
                .auction(auctionConverter.toDTO(bom.getAuction()))
                .bid(bidConverter.toDTO(bom.getBid()))
                .time(bom.getTime())
                .build();
    }
}
