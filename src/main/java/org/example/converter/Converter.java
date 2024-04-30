package org.example.converter;

public interface Converter<DTO, BOM> {
    BOM fromDTO(DTO DTO);
    DTO toDTO(BOM BOM);
}
