package org.example.service.VehicleService;

import org.example.bom.Vehicle;
import org.example.exception.NotFoundException;

public interface VehicleService {

    String printVehicle(Vehicle vehicle);

    Vehicle save(Vehicle vehicle) throws NotFoundException;
}
