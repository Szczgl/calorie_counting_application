package com.calories.end.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
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
    private String quantity;

    @Column(name = "CALORIES")
    private int calories;

    @Column(name = "SUGAR")
    private int sugar;

    @Column(name = "SALT")
    private int salt;

    @Column(name = "CHOLESTEROL")
    private int cholesterol;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.LAZY)
    private Set<Recipe> recipes = new HashSet<>();

    public Ingredient(Long id, String name, String quantity, int calories, int sugar, int salt, int cholesterol) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
        this.sugar = sugar;
        this.salt = salt;
        this.cholesterol = cholesterol;
    }
}