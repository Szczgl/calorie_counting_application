package com.calories.end.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACTIVITY")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIVITY_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONSUMED_CALORIES")
    private double consumedCalories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    public Activity(Long id, String name, String description, double consumedCalories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.consumedCalories = consumedCalories;
    }
}