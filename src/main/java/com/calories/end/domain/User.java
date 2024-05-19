package com.calories.end.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private int dailyCalorieIntake;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Recipe> recipes;

    public User(Long id, String username, String email, int dailyCalorieIntake) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.dailyCalorieIntake = dailyCalorieIntake;
    }
}
