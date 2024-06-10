package com.calories.end.repository;

import com.calories.end.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    boolean existsByIngredients_Id(Long ingredientId);
}

