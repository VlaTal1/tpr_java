package org.example.service.VehicleService;

import org.example.bom.Vehicle;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleNotFoundException;

public interface VehicleService {

    String printVehicle(Vehicle vehicle);

    Vehicle save(Vehicle vehicle) throws NotFoundException;

    Vehicle update(Vehicle vehicle);

    Vehicle findById(Long id) throws NotFoundException;

    boolean isAvailable(Long id) throws NotFoundException;

    Vehicle getById(Long vehicleId) throws VehicleNotFoundException;
}
