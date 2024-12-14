package org.example.connector;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.bom.*;
import org.example.service.GetVehicleByIdRequest;
import org.example.service.GetVehicleByIdResponse;
import org.example.service.VehicleServiceGrpc;
import org.example.service.VehicleSoldRequest;
import org.springframework.stereotype.Service;

@Service
public class VehicleGrpcConnector {

    private final VehicleServiceGrpc.VehicleServiceBlockingStub stub;

    public VehicleGrpcConnector() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        this.stub = VehicleServiceGrpc.newBlockingStub(channel);
    }

    public Vehicle getVehicleById(long vehicleId) {
        GetVehicleByIdRequest request = GetVehicleByIdRequest.newBuilder()
                .setVehicleId(vehicleId)
                .build();

        GetVehicleByIdResponse response = this.stub.getVehicleById(request);

        Country country = Country.builder()
                .id(response.getModel().getManufacturer().getCountry().getId())
                .name(response.getModel().getManufacturer().getCountry().getName())
                .build();

        Manufacturer manufacturer = Manufacturer.builder()
                .id(response.getModel().getManufacturer().getId())
                .name(response.getModel().getManufacturer().getName())
                .country(country)
                .build();

        Model model = Model.builder()
                .id(response.getModel().getId())
                .name(response.getModel().getName())
                .manufacturer(manufacturer)
                .build();

        Color color = Color.builder()
                .id(response.getColor().getId())
                .name(response.getColor().getName())
                .build();

        Vehicle vehicle;

        switch (Type.valueOf(response.getType())) {
            case Type.MOTORCYCLE -> vehicle = new Motorcycle();
            case Type.TRUCK -> vehicle = new Truck();
            default -> vehicle = new PassengerCar();
        }

        vehicle.setId(response.getId());
        vehicle.setAmount(response.getAmount());
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setPrice(response.getPrice());
        vehicle.setYear(response.getYear());
        vehicle.setUsed(response.getIsUsed());

        return vehicle;
    }

    public void markVehicleAsSold(long vehicleId) {
        VehicleSoldRequest request = VehicleSoldRequest.newBuilder()
                .setVehicleId(vehicleId)
                .build();

        this.stub.vehicleSold(request);
    }
}
