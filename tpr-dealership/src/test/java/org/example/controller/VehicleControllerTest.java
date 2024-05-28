package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bom.*;
import org.example.converter.Converter;
import org.example.dto.web.VehicleRequest;
import org.example.exception.NotFoundException;
import org.example.service.VehicleService.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private Converter<VehicleRequest, Vehicle> vehicleRequestConverter;

    @Test
    void addTest() throws Exception {
        Model model = new Model();
        Color color = new Color();
        VehicleRequest vehicleRequest = new VehicleRequest(1L, 5000, model, color, 6000F, 2022, Type.PASSENGER_CAR, false);

        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022, false);
        when(vehicleRequestConverter.fromDTO(any(VehicleRequest.class))).thenReturn(vehicle);
        when(vehicleService.save(any(Vehicle.class))).thenReturn(vehicle);

        String vehicleRequestJson = objectMapper.writeValueAsString(vehicleRequest);
        String vehicleJson = objectMapper.writeValueAsString(vehicle);

        mockMvc.perform(post("/api/vehicle/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(vehicleJson));
    }

    @Test
    void addTest_NotFound() throws Exception {
        Model model = new Model();
        Color color = new Color();
        VehicleRequest vehicleRequest = new VehicleRequest(1L, 5000, model, color, 6000F, 2022, Type.PASSENGER_CAR, false);

        Vehicle vehicle = new PassengerCar(1L, 5000, model, color, 6000, 2022, false);
        when(vehicleRequestConverter.fromDTO(any(VehicleRequest.class))).thenReturn(vehicle);
        when(vehicleService.save(any(Vehicle.class))).thenThrow(new NotFoundException("Vehicle not found"));

        String vehicleRequestJson = objectMapper.writeValueAsString(vehicleRequest);

        mockMvc.perform(post("/api/vehicle/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehicleRequestJson))
                .andExpect(status().isNotFound());
    }
}
