package com.calories.end.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "INGREDIENTS")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INGREDIENT_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "QUANTITY")
    private double quantity;

    @Column(name = "CALORIES")
    private double calories;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private Set<Recipe> recipes = new HashSet<>();

    public Ingredient(Long id, String name, double quantity, double calories) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
    }
}