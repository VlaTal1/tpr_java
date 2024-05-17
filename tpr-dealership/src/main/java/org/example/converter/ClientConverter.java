package org.example.converter;

import org.example.bom.Client;
import org.example.dto.db.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter implements Converter<ClientDTO, Client> {

    @Override
    public Client fromDTO(ClientDTO DTO) {
        return Client.builder()
                .id(DTO.getId())
                .name(DTO.getName())
                .phone(DTO.getPhone())
                .address(DTO.getAddress())
                .passport(DTO.getPassport())
                .discount(DTO.getDiscount())
                .build();
    }

    @Override
    public ClientDTO toDTO(Client BOM) {
        return ClientDTO.builder()
                .id(BOM.getId())
                .name(BOM.getName())
                .phone(BOM.getPhone())
                .address(BOM.getAddress())
                .passport(BOM.getPassport())
                .discount(BOM.getDiscount())
                .build();
    }
}
