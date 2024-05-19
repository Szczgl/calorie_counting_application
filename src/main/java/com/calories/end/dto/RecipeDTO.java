package com.calories.end.dto;

import lombok.*;

import java.util.*;

@Data
public class RecipeDTO {

    private Long id;
    private String name;
    private String description;
    private List<IngredientDTO> ingredients;
}
