package org.example.service.VehicleService;

import lombok.RequiredArgsConstructor;
import org.example.bom.*;
import org.example.converter.Converter;
import org.example.dto.db.VehicleDTO;
import org.example.exception.NotFoundException;
import org.example.exception.VehicleNotFoundException;
import org.example.printer.MotorcyclePrinter;
import org.example.printer.PassengerCarPrinter;
import org.example.printer.TruckPrinter;
import org.example.printer.VehiclePrinter;
import org.example.repository.VehicleRepository;
import org.example.service.ColorService.ColorService;
import org.example.service.CountryService.CountryService;
import org.example.service.ManufacturerService.ManufacturerService;
import org.example.service.ModelService.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final Map<Class<? extends Vehicle>, VehiclePrinter> printers;

    private final Converter<VehicleDTO, Vehicle> vehicleConverter;

    private final VehicleRepository vehicleRepository;

    private final ColorService colorService;

    private final ModelService modelService;

    private final ManufacturerService manufacturerService;

    private final CountryService countryService;

    @Autowired
    public VehicleServiceImpl(Converter<VehicleDTO, Vehicle> vehicleConverter, VehicleRepository vehicleRepository, ColorService colorServiceImpl, ModelService modelServiceImpl, ManufacturerService manufacturerServiceImpl, CountryService countryServiceImpl) {
        this.printers = new HashMap<>();
        printers.put(PassengerCar.class, new PassengerCarPrinter());
        printers.put(Truck.class, new TruckPrinter());
        printers.put(Motorcycle.class, new MotorcyclePrinter());

        this.vehicleConverter = vehicleConverter;
        this.vehicleRepository = vehicleRepository;
        this.colorService = colorServiceImpl;
        this.modelService = modelServiceImpl;
        this.manufacturerService = manufacturerServiceImpl;
        this.countryService = countryServiceImpl;
    }

    @Override
    public String printVehicle(Vehicle vehicle) {
        VehiclePrinter printer = printers.get(vehicle.getClass());
        if (printer != null) {
            return printer.generatePrintableString(vehicle);
        } else {
            return "No printer found";
        }
    }

    @Override
    public Vehicle save(Vehicle vehicle) throws NotFoundException {
        checkCanSave(vehicle);
        VehicleDTO vehicleDTO = vehicleConverter.toDTO(vehicle);
        vehicleDTO.setId(null);
        VehicleDTO saved = vehicleRepository.save(vehicleDTO);
        return vehicleConverter.fromDTO(saved);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        VehicleDTO vehicleDTO = vehicleConverter.toDTO(vehicle);
        VehicleDTO updated = vehicleRepository.save(vehicleDTO);
        return vehicleConverter.fromDTO(updated);
    }

    @Override
    public Vehicle findById(Long id) throws NotFoundException {
        VehicleDTO vehicleDTO = vehicleRepository.findById(id).orElseThrow(() -> new NotFoundException(STR."Vehicle with id \{id} not found"));
        return vehicleConverter.fromDTO(vehicleDTO);
    }

    // TODO test for throw
    @Override
    public boolean isAvailable(Long id) throws NotFoundException {
        VehicleDTO vehicleDTO = vehicleRepository.findById(id).orElse(null);
        if (vehicleDTO == null) throw new NotFoundException(STR."Vehicle with id \{id} not found");
        return vehicleDTO.getAmount() > 0;
    }

    @Override
    public VehicleDTO getById(Long vehicleId) throws VehicleNotFoundException {
        return vehicleRepository.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(STR."Vehicle with id \{vehicleId} not found"));
    }

    private void checkCanSave(Vehicle vehicle) throws NotFoundException {
        Color color = colorService.findById(vehicle.getColor().getId());
        if (color == null)
            // TODO test for throw
            throw new NotFoundException(STR."Color with id \{vehicle.getColor().getId()} not found");

        Model model = modelService.findById(vehicle.getModel().getId());
        if (model == null)
            // TODO test for throw
            throw new NotFoundException(STR."Model with id \{vehicle.getModel().getId()} not found");

        Manufacturer manufacturer = manufacturerService.findById(vehicle.getModel().getManufacturer().getId());
        if (manufacturer == null)
            // TODO test for throw
            throw new NotFoundException(STR."Manufacturer with id \{vehicle.getModel().getManufacturer().getId()} not found");

        Country country = countryService.findById(vehicle.getModel().getManufacturer().getCountry().getId());
        if (country == null)
            // TODO test for throw
            throw new NotFoundException(STR."Country with id \{vehicle.getModel().getManufacturer().getCountry().getId()} not found");
    }
}

//public class VehicleService {
//
//    public String printVehicle(Vehicle vehicle) {
//        if (vehicle instanceof PassengerCar) {
//            PassengerCarPrinter printer = new PassengerCarPrinter();
//            return printer.print(vehicle);
//        } else if (vehicle instanceof Truck) {
//            TruckPrinter printer = new TruckPrinter();
//            return printer.print(vehicle);
//        } else if (vehicle instanceof Motorcycle) {
//            MotorcyclePrinter printer = new MotorcyclePrinter();
//            return printer.print(vehicle);
//        } else {
//            return "No printer found for vehicle type: " + vehicle.getClass().getSimpleName();
//        }
//    }
//}
