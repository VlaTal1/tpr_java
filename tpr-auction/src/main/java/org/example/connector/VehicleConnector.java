package org.example.connector;

import org.example.bom.Vehicle;
import org.example.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${dealership-url}", name = "vehicleConnector", configuration = FeignConfig.class)
public interface VehicleConnector {

    @GetMapping("vehicle/{vehicleId}")
    Vehicle get(@PathVariable("vehicleId") Long vehicleId);
}
