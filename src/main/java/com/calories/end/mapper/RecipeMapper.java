package com.calories.end.mapper;

import com.calories.end.domain.Ingredient;
import com.calories.end.domain.Recipe;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeMapper {

    @Autowired
    private IngredientRepository ingredientRepository;

    public RecipeDTO mapToRecipeDto(Recipe recipe) {
        RecipeDTO recipeDto = new RecipeDTO();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setTotalCalories(recipe.getTotalCalories());
        recipeDto.setUserId(recipe.getUser().getId());
        recipeDto.setIngredientIds(recipe.getIngredients().stream()
                .map(Ingredient::getId)
                .collect(Collectors.toSet()));

        return recipeDto;
    }

    public Recipe mapToRecipe(RecipeDTO recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setTotalCalories(recipeDto.getTotalCalories());

        Set<Ingredient> ingredients = recipeDto.getIngredientIds().stream()
                .map(id -> ingredientRepository.findById(id).orElseThrow(() -> new RuntimeException("Ingredient not found")))
                .collect(Collectors.toSet());
        recipe.setIngredients(ingredients);

        return recipe;
    }
}

