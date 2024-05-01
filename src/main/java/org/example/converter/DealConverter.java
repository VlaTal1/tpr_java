package org.example.converter;

import lombok.RequiredArgsConstructor;
import org.example.bom.*;
import org.example.dto.db.ClientDTO;
import org.example.dto.db.DealDTO;
import org.example.dto.db.EmployeeDTO;
import org.example.dto.db.VehicleDTO;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DealConverter implements Converter<DealDTO, Deal> {

    private final Converter<VehicleDTO, Vehicle> vehicleConverter;

    private final Converter<ClientDTO, Client> clientConverter;

    private final Converter<EmployeeDTO, Employee> employeeConverter;

    @Override
    public Deal fromDTO(DealDTO DTO) {
        return Deal.builder()
                .id(DTO.getId())
                .vehicle(vehicleConverter.fromDTO(DTO.getVehicle()))
                .client(clientConverter.fromDTO(DTO.getClient()))
                .employee(employeeConverter.fromDTO(DTO.getEmployee()))
                .date(DTO.getDate())
                .totalPrice(DTO.getTotalPrice())
                .paymentType(PaymentType.valueOf(DTO.getPaymentType()))
                .monthlyPayment(DTO.getMonthlyPayment())
                .paid(DTO.getPaid())
                .build();
    }

    @Override
    public DealDTO toDTO(Deal BOM) {
        return DealDTO.builder()
                .id(BOM.getId())
                .vehicle(vehicleConverter.toDTO(BOM.getVehicle()))
                .client(clientConverter.toDTO(BOM.getClient()))
                .employee(employeeConverter.toDTO(BOM.getEmployee()))
                .date(BOM.getDate())
                .totalPrice(BOM.getTotalPrice())
                .paymentType(BOM.getPaymentType().toString())
                .monthlyPayment(BOM.getMonthlyPayment())
                .paid(BOM.getPaid())
                .build();
    }
}
