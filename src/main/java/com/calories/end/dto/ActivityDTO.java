package com.calories.end.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDTO {
    private Long id;
    private String name;
    private String description;
    private double consumedCalories;
    private Long userId;

    public ActivityDTO(Long id, String name, String description, double consumedCalories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.consumedCalories = consumedCalories;
    }
}
