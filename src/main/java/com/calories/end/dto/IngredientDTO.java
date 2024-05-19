package com.calories.end.dto;

import lombok.*;

@Data
public class IngredientDTO {

    private Long id;
    private String name;
    private double calories;
    private double sugar;
    private double salt;
    private double cholesterol;
}
