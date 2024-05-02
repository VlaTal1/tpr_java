package org.example.dto.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.bom.Color;
import org.example.bom.Model;
import org.example.bom.Type;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {

    @JsonProperty(required = false)
    private Long id;

    private int amount;

    private Model model;

    private Color color;

    private float price;

    private int year;

    private Type type;
}