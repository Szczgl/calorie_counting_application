package com.calories.end.domain;

import com.calories.end.domain.observer.Observer;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "DAILY_CALORIE_INTAKE")
    private double dailyCalorieIntake;

    @Column(name = "DAILY_CALORIE_CONSUMPTION")
    private double dailyCalorieConsumption;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activity;

    private transient List<Observer> observers = new ArrayList<>();

    public User(Long id, String username, String email, double dailyCalorieIntake, double dailyCalorieConsumption) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dailyCalorieIntake = dailyCalorieIntake;
        this.dailyCalorieConsumption = dailyCalorieConsumption;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        notifyObservers();
    }

    public void addActivity(Activity activity) {
        this.activity.add(activity);
        notifyObservers();
    }

    public double calculateDailyCalorieConsumption() {
        double totalRecipeCalories = recipes.stream()
                .mapToDouble(Recipe::getTotalCalories)
                .sum();
        double totalActivityCalories = activity.stream()
                .mapToDouble(Activity::getConsumedCalories)
                .sum();
        return totalRecipeCalories - totalActivityCalories;
    }

    @PostLoad
    public void updateDailyCalorieConsumption() {
        this.dailyCalorieConsumption = calculateDailyCalorieConsumption();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", dailyCalorieIntake=" + dailyCalorieIntake +
                ", dailyCalorieConsumption=" + dailyCalorieConsumption +
                '}';
    }
}
