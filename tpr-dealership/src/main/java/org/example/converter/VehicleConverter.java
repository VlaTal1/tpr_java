package org.example.converter;

import lombok.RequiredArgsConstructor;
import org.example.bom.*;
import org.example.dto.db.ColorDTO;
import org.example.dto.db.ModelDTO;
import org.example.dto.db.VehicleDTO;
import org.example.factory.MotorcycleFactory;
import org.example.factory.PassengerCarFactory;
import org.example.factory.TruckFactory;
import org.example.factory.VehicleFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehicleConverter implements Converter<VehicleDTO, Vehicle> {

    private final Converter<ModelDTO, Model> modelConverter;

    private final Converter<ColorDTO, Color> colorConverter;

    private VehicleFactory vehicleFactory;

    @Override
    public Vehicle fromDTO(VehicleDTO DTO) {
        if (DTO.getType().equals(Type.MOTORCYCLE.toString())) {
            vehicleFactory = new MotorcycleFactory();
        } else if (DTO.getType().equals(Type.PASSENGER_CAR.toString())) {
            vehicleFactory = new PassengerCarFactory();
        } else if (DTO.getType().equals(Type.TRUCK.toString())) {
            vehicleFactory = new TruckFactory();
        }
        return vehicleFactory.createVehicle(
                DTO.getId(),
                DTO.getAmount(),
                modelConverter.fromDTO(DTO.getModel()),
                colorConverter.fromDTO(DTO.getColor()),
                DTO.getPrice(),
                DTO.getYear());
    }

    @Override
    public VehicleDTO toDTO(Vehicle BOM) {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .id(BOM.getId())
                .amount(BOM.getAmount())
                .model(modelConverter.toDTO(BOM.getModel()))
                .color(colorConverter.toDTO(BOM.getColor()))
                .price(BOM.getPrice())
                .year(BOM.getYear())
                .build();

        if (BOM instanceof Motorcycle) {
            vehicleDTO.setType(Type.MOTORCYCLE.toString());
        } else if (BOM instanceof PassengerCar) {
            vehicleDTO.setType(Type.PASSENGER_CAR.toString());
        } else if (BOM instanceof Truck) {
            vehicleDTO.setType(Type.TRUCK.toString());
        }

        return vehicleDTO;
    }
}
