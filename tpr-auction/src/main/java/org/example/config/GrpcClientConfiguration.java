//package org.example.config;
//
//import net.devh.boot.grpc.client.inject.GrpcClient;
//import org.example.connector.VehicleGrpcConnector;
//import org.example.service.VehicleServiceGrpc;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GrpcClientConfiguration {
//    @GrpcClient("hello")
//    VehicleServiceGrpc.VehicleServiceBlockingStub vehicleClient;
//
//    @Bean
//    VehicleGrpcConnector vehicleGrpcConnector() {
//        return new VehicleGrpcConnector(vehicleClient);
//    }
//}
