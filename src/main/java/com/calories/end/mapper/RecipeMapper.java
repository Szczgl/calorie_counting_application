package com.calories.end.mapper;

import com.calories.end.domain.Ingredient;
import com.calories.end.domain.Recipe;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeMapper {

    private final IngredientRepository ingredientRepository;

    public RecipeDTO mapToRecipeDto(Recipe recipe) {
        RecipeDTO recipeDto = new RecipeDTO();
        recipeDto.setId(recipe.getId());
        recipeDto.setName(recipe.getName());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setTotalCalories(recipe.getTotalCalories());
        recipeDto.setUserId(recipe.getUser().getId());
        recipeDto.setIngredients(recipe.getIngredients().stream()
                .map(this::mapToIngredientDto)
                .collect(Collectors.toList()));
        return recipeDto;
    }

    public Recipe mapToRecipe(RecipeDTO recipeDto) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDto.getId());
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setTotalCalories(recipeDto.getTotalCalories());

        Set<Ingredient> ingredients = recipeDto.getIngredients().stream()
                .map(this::findOrCreateIngredient)
                .collect(Collectors.toSet());
        recipe.setIngredients(ingredients);
        return recipe;
    }

    private IngredientDTO mapToIngredientDto(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getCalories()
        );
    }

    private Ingredient findOrCreateIngredient(IngredientDTO ingredientDto) {
        return ingredientRepository.findByName(ingredientDto.getName())
                .orElseGet(() -> new Ingredient(
                        null,
                        ingredientDto.getName(),
                        ingredientDto.getQuantity(),
                        ingredientDto.getCalories()
                ));
    }
}

