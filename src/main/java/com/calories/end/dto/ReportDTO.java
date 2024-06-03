package com.calories.end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

    private String username;
    private LocalDate reportDate;
    private double dailyCalorieIntake;
    private double dailyCalorieConsumption;
    private double calorieBalance;
    private List<String> recipesName;
}
