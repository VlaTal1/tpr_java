package org.example.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.bom.*;
import org.example.exception.VehicleNotFoundException;
import org.example.service.VehicleService.VehicleService;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class VehicleGrpcService extends VehicleServiceGrpc.VehicleServiceImplBase {
    private final VehicleService vehicleService;

    @Override
    public void getVehicleById(GetVehicleByIdRequest request, StreamObserver<GetVehicleByIdResponse> responseObserver) {
        log.info(STR."Grpc server get request for vehicle with id \{request.getVehicleId()}");
        try {
            Vehicle vehicle = vehicleService.get(request.getVehicleId());

            Country country = Country.newBuilder()
                    .setId(vehicle.getModel().getManufacturer().getCountry().getId())
                    .setName(vehicle.getModel().getManufacturer().getCountry().getName())
                    .build();

            Manufacturer manufacturer = Manufacturer.newBuilder()
                    .setId(vehicle.getModel().getManufacturer().getId())
                    .setName(vehicle.getModel().getManufacturer().getName())
                    .setCountry(country)
                    .build();

            Model model = Model.newBuilder()
                    .setId(vehicle.getModel().getId())
                    .setName(vehicle.getModel().getName())
                    .setManufacturer(manufacturer)
                    .build();

            Color color = Color.newBuilder()
                    .setId(vehicle.getColor().getId())
                    .setName(vehicle.getColor().getName())
                    .build();

            String type;

            switch (vehicle) {
                case Truck _ -> type = Type.TRUCK.toString();
                case Motorcycle _ -> type = Type.MOTORCYCLE.toString();
                default -> type = Type.PASSENGER_CAR.toString();
            }

            GetVehicleByIdResponse response = GetVehicleByIdResponse.newBuilder()
                    .setId(vehicle.getId())
                    .setAmount(vehicle.getAmount())
                    .setModel(model)
                    .setColor(color)
                    .setPrice(vehicle.getPrice())
                    .setYear(vehicle.getYear())
                    .setIsUsed(vehicle.isUsed())
                    .setType(type)
                    .build();

            log.info(STR."Grpc server return vehicle with id \{request.getVehicleId()}");
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (VehicleNotFoundException e) {
            log.error(e.getMessage());
            responseObserver.onError(e);
        }
    }

    @Override
    public void vehicleSold(VehicleSoldRequest request, StreamObserver<Empty> responseObserver) {
        log.info(STR."Grpc get request for vehicle with id \{request.getVehicleId()} sold");
        try {
            vehicleService.vehicleSold(request.getVehicleId());
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (VehicleNotFoundException e) {
            log.error(e.getMessage());
            responseObserver.onError(e);
        }
    }
}

