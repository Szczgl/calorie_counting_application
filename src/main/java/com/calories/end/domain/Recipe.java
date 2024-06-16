package com.calories.end.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECIPES", schema = "PUBLIC")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECIPE_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TOTAL_CALORIES")
    private double totalCalories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "RECIPE_INGREDIENT",
            joinColumns = @JoinColumn(name = "RECIPE_ID"),
            inverseJoinColumns = @JoinColumn(name = "INGREDIENT_ID"))
    @JsonManagedReference
    private Set<Ingredient> ingredients = new HashSet<>();

    public Recipe(Long id, String name, String description, double totalCalories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.totalCalories = totalCalories;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", totalCalories=" + totalCalories +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }
}
