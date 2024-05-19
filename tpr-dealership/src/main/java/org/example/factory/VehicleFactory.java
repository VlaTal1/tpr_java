package org.example.factory;

import org.example.bom.Color;
import org.example.bom.Model;
import org.example.bom.Vehicle;

public interface VehicleFactory {

    Vehicle createVehicle(Long id, int amount, Model model, Color color, float price, int year, boolean isUsed);
}
