package org.example.converter;

import lombok.RequiredArgsConstructor;
import org.example.bom.Manufacturer;
import org.example.bom.Model;
import org.example.dto.db.ManufacturerDTO;
import org.example.dto.db.ModelDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModelConverter implements Converter<ModelDTO, Model> {

    private final Converter<ManufacturerDTO, Manufacturer> manufacturerConverter;

    @Override
    public Model fromDTO(ModelDTO DTO) {
        if (DTO == null) return null;
        return Model.builder()
                .id(DTO.getId())
                .name(DTO.getName())
                .manufacturer(manufacturerConverter.fromDTO(DTO.getManufacturer()))
                .build();
    }

    @Override
    public ModelDTO toDTO(Model BOM) {
        return ModelDTO.builder()
                .id(BOM.getId())
                .name(BOM.getName())
                .manufacturer(manufacturerConverter.toDTO(BOM.getManufacturer()))
                .build();
    }
}
