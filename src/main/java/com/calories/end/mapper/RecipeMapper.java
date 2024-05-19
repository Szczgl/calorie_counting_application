package com.calories.end.mapper;

import com.calories.end.domain.Recipe;
import com.calories.end.dto.RecipeDTO;
import org.springframework.stereotype.Component;

@Component
public class RecipeMapper {

    public RecipeDTO toDto(Recipe recipe) {
        return new RecipeDTO();
    }

    public Recipe toEntity(RecipeDTO recipeDTO) {
        return new Recipe();
    }
}

