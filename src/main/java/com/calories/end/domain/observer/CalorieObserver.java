package com.calories.end.domain.observer;

import com.calories.end.domain.User;
import com.calories.end.domain.Recipe;
import com.calories.end.domain.Activity;

public class CalorieObserver implements Observer {
    @Override
    public void update(User user) {
        double totalIntake = user.getRecipes().stream().mapToDouble(Recipe::getTotalCalories).sum();
        double totalConsumption = user.getActivity().stream().mapToDouble(Activity::getConsumedCalories).sum();
        user.setDailyCalorieIntake(totalIntake);
        user.setDailyCalorieConsumption(totalConsumption);
    }
}
