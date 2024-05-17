package org.example.converter;

import org.example.bom.Type;
import org.example.bom.Vehicle;
import org.example.dto.web.VehicleRequest;
import org.example.factory.MotorcycleFactory;
import org.example.factory.PassengerCarFactory;
import org.example.factory.TruckFactory;
import org.example.factory.VehicleFactory;
import org.springframework.stereotype.Component;

@Component
public class VehicleRequestConverter implements Converter<VehicleRequest, Vehicle> {

    private VehicleFactory vehicleFactory;

    @Override
    public Vehicle fromDTO(VehicleRequest DTO) {
        if (DTO.getType().equals(Type.MOTORCYCLE)) {
            vehicleFactory = new MotorcycleFactory();
        }
        else if (DTO.getType().equals(Type.PASSENGER_CAR)) {
            vehicleFactory = new PassengerCarFactory();
        }
        else if (DTO.getType().equals(Type.TRUCK)) {
            vehicleFactory = new TruckFactory();
        }
        return vehicleFactory.createVehicle(
                DTO.getId(),
                DTO.getAmount(),
                DTO.getModel(),
                DTO.getColor(),
                DTO.getPrice(),
                DTO.getYear());
    }

    @Override
    public VehicleRequest toDTO(Vehicle BOM) {
        throw new UnsupportedOperationException("VehicleRequestConverter.toDTO in not implemented");
    }
}
