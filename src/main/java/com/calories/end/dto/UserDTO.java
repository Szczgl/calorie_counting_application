package com.calories.end.dto;

import lombok.*;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private double dailyCalorieRequirement;
}
