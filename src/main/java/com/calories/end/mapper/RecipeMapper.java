package com.calories.end.mapper;

import com.calories.end.domain.Recipe;
import com.calories.end.dto.RecipeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeMapper {

    private final IngredientMapper ingredientMapper;

    public RecipeDTO mapToRecipeDto(Recipe recipe) {
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getTotalCalories(),
                recipe.getTranslatedDescription(),
                recipe.getUser().getId(),
                ingredientMapper.mapToIngredientDtoList(recipe.getIngredients())
        );
    }

    public Recipe mapToRecipe(RecipeDTO recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setTotalCalories(recipeDto.getTotalCalories());
        recipe.setTranslatedDescription(recipeDto.getTranslatedDescription());
        return recipe;
    }
}

