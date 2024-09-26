package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Vehicle;
import org.example.converter.Converter;
import org.example.dto.web.VehicleRequest;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleNotFoundException;
import org.example.service.VehicleService.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {

    private final VehicleService vehicleService;

    private final Converter<VehicleRequest, Vehicle> vehicleRequestConverter;

    @GetMapping("/")
    public ResponseEntity<List<Vehicle>> getAll() {
        List<Vehicle> vehicles = vehicleService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(vehicles);
    }

    @PostMapping("/")
    public ResponseEntity<Vehicle> add(
            @RequestBody VehicleRequest vehicleRequest) throws NotFoundException {
        Vehicle savedVehicle = vehicleService.save(vehicleRequestConverter.fromDTO(vehicleRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> get(@PathVariable("vehicleId") Long vehicleId) throws VehicleNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.get(vehicleId));
    }

    @PutMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> update(
            @PathVariable("vehicleId") Long vehicleId,
            @RequestBody VehicleRequest vehicleRequest) throws VehicleNotFoundException {
        Vehicle savedVehicle = vehicleService.update(vehicleId, vehicleRequestConverter.fromDTO(vehicleRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }

    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> delete(
            @PathVariable("vehicleId") Long vehicleId) throws VehicleNotFoundException {
        Vehicle vehicle = vehicleService.delete(vehicleId);
        return ResponseEntity.status(HttpStatus.OK).body(vehicle);
    }
}
