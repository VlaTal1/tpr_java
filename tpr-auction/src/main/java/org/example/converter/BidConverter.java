package org.example.converter;

import lombok.RequiredArgsConstructor;
import org.example.bom.Bid;
import org.example.dto.db.BidDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BidConverter implements Converter<BidDTO, Bid> {

    private final ClientConverter clientConverter;

    @Override
    public Bid fromDTO(BidDTO dto) {
        return Bid.builder()
                .id(dto.getId())
                .client(clientConverter.fromDTO(dto.getClient()))
                .amount(dto.getAmount())
                .build();
    }

    @Override
    public BidDTO toDTO(Bid bom) {
        return BidDTO.builder()
                .id(bom.getId())
                .client(clientConverter.toDTO(bom.getClient()))
                .amount(bom.getAmount())
                .build();
    }
}
