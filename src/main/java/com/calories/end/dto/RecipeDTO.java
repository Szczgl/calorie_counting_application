package com.calories.end.dto;

import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDTO {
    private Long id;
    private String name;
    private String description;
    private int totalCalories;
    private String translatedDescription;
    private Long userId;
    private List<IngredientDTO> ingredients;
}
