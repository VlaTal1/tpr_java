package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bom.Vehicle;
import org.example.converter.Converter;
import org.example.dto.db.VehicleDTO;
import org.example.dto.web.VehicleRequest;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleNotFoundException;
import org.example.service.VehicleService.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    private final Converter<VehicleRequest, Vehicle> vehicleRequestConverter;

    @PostMapping("/")
    public ResponseEntity<Vehicle> add(
            @RequestBody VehicleRequest vehicleRequest) throws NotFoundException {
        Vehicle savedVehicle = vehicleService.save(vehicleRequestConverter.fromDTO(vehicleRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVehicle);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleDTO> get(@PathVariable("vehicleId") Long vehicleId) throws VehicleNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getById(vehicleId));
    }
}
