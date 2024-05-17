package org.example.factory;

import org.example.bom.Color;
import org.example.bom.Model;
import org.example.bom.Truck;
import org.example.bom.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class TruckFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(Long id, int amount, Model model, Color color, float price, int year) {
        return new Truck(id, amount, model, color, price, year);
    }
}
