package org.example.converter;

import org.example.bom.Client;
import org.example.dto.db.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter implements Converter<ClientDTO, Client> {

    @Override
    public Client fromDTO(ClientDTO dto) {
        return Client.builder()
                .id(dto.getId())
                .name(dto.getName())
                .address(dto.getAddress())
                .phone(dto.getPhone())
                .passport(dto.getPassport())
                .build();
    }

    @Override
    public ClientDTO toDTO(Client bom) {
        return ClientDTO.builder()
                .id(bom.getId())
                .name(bom.getName())
                .address(bom.getAddress())
                .phone(bom.getPhone())
                .passport(bom.getPassport())
                .build();
    }
}
