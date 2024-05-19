package com.calories.end.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
    private Long id;
    private String name;
    private String quantity;
    private int calories;
    private int sugar;
    private int salt;
    private int cholesterol;
}
