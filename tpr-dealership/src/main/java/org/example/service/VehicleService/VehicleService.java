package org.example.service.VehicleService;

import org.example.bom.Vehicle;
import org.example.dto.db.VehicleDTO;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleNotFoundException;

import java.util.List;

public interface VehicleService {

    String printVehicle(Vehicle vehicle);

    Vehicle save(Vehicle vehicle) throws NotFoundException;

    List<Vehicle> getAll();

    Vehicle update(Long vehicleId, Vehicle vehicle) throws VehicleNotFoundException;

    Vehicle findById(Long id) throws NotFoundException;

    boolean isAvailable(Long id) throws NotFoundException;

    VehicleDTO getById(Long vehicleId) throws VehicleNotFoundException;

    Vehicle get(Long vehicleId) throws VehicleNotFoundException;

    Vehicle delete(Long vehicleId) throws VehicleNotFoundException;

    void vehicleSold(Long vehicleId) throws VehicleNotFoundException;
}
