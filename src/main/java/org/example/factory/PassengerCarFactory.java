package org.example.factory;

import org.example.bom.Color;
import org.example.bom.Model;
import org.example.bom.PassengerCar;
import org.example.bom.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class PassengerCarFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(Long id, int amount, Model model, Color color, double price, int year) {
        return new PassengerCar(id, amount, model, color, price, year);
    }
}
