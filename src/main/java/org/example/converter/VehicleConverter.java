package org.example.converter;

import lombok.RequiredArgsConstructor;
import org.example.bom.*;
import org.example.dto.db.ColorDTO;
import org.example.dto.db.ModelDTO;
import org.example.dto.db.VehicleDTO;
import org.example.dto.db.VehicleTypeDTO;
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
        if (DTO.getVehicleType().getName().equals(Type.MOTORCYCLE)) {
            vehicleFactory = new MotorcycleFactory();
        }
        else if (DTO.getVehicleType().getName().equals(Type.PASSENGER_CAR)) {
            vehicleFactory = new PassengerCarFactory();
        }
        else if (DTO.getVehicleType().getName().equals(Type.TRUCK)) {
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

        VehicleTypeDTO vehicleTypeDTO = VehicleTypeDTO.builder().build();
        if (BOM instanceof Motorcycle) {
            vehicleTypeDTO.setName(Type.MOTORCYCLE.toString());
        }
        else if (BOM instanceof PassengerCar) {
            vehicleTypeDTO.setName(Type.PASSENGER_CAR.toString());
        }
        else if (BOM instanceof Truck) {
            vehicleTypeDTO.setName(Type.TRUCK.toString());
        }
        vehicleDTO.setVehicleType(vehicleTypeDTO);

        return vehicleDTO;
    }
}
