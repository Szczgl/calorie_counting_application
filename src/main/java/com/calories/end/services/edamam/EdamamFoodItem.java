package com.calories.end.services.edamam;

public class EdamamFoodItem {

    private String label;
    private EdamamNutrients nutrients;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public EdamamNutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(EdamamNutrients nutrients) {
        this.nutrients = nutrients;
    }
}