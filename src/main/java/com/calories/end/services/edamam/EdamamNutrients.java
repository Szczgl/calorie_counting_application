package com.calories.end.services.edamam;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EdamamNutrients {

    @JsonProperty("ENERC_KCAL")
    private double calories;

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }
}
