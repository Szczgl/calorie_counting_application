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
    private double totalCalories;
    private Long userId;
    private Set<Long> ingredientIds;

    public RecipeDTO(Long id, String name, String description, double totalCalories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalCalories = totalCalories;
    }
}
