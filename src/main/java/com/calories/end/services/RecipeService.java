package com.calories.end.services;

import com.calories.end.domain.Ingredient;
import com.calories.end.domain.Recipe;
import com.calories.end.domain.User;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.exception.RecipeNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.RecipeMapper;
import com.calories.end.repository.IngredientRepository;
import com.calories.end.repository.RecipeRepository;
import com.calories.end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::mapToRecipeDto)
                .collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(Long id) throws RecipeNotFoundException {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
        return recipeMapper.mapToRecipeDto(recipe);
    }

    @Transactional
    public RecipeDTO saveRecipe(RecipeDTO recipeDto) throws UserNotFoundException {
        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
        User user = userRepository.findById(recipeDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + recipeDto.getUserId()));
        recipe.setUser(user);

        Set<Ingredient> ingredients = recipeDto.getIngredients().stream()
                .map(this::findOrCreateIngredient)
                .collect(Collectors.toSet());
        recipe.setIngredients(ingredients);

        recipe.setTotalCalories(ingredients.stream()
                .mapToDouble(ingredient -> ingredient.getCalories() * ingredient.getQuantity() / 100)
                .sum());

        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.mapToRecipeDto(savedRecipe);
    }

    @Transactional
    public RecipeDTO replaceRecipe(RecipeDTO recipeDto, Long id) throws RecipeNotFoundException, UserNotFoundException {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));

        existingRecipe.setName(recipeDto.getName());
        existingRecipe.setDescription(recipeDto.getDescription());

        User user = userRepository.findById(recipeDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + recipeDto.getUserId()));
        existingRecipe.setUser(user);

        Set<Ingredient> ingredients = recipeDto.getIngredients().stream()
                .map(this::findOrCreateIngredient)
                .collect(Collectors.toSet());
        existingRecipe.setIngredients(ingredients);

        existingRecipe.setTotalCalories(ingredients.stream()
                .mapToDouble(ingredient -> ingredient.getCalories() * ingredient.getQuantity() / 100)
                .sum());

        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeMapper.mapToRecipeDto(savedRecipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    private Ingredient findOrCreateIngredient(IngredientDTO ingredientDto) {
        return ingredientRepository.findByName(ingredientDto.getName())
                .orElseGet(() -> ingredientRepository.save(new Ingredient(
                        null,
                        ingredientDto.getName(),
                        ingredientDto.getQuantity(),
                        ingredientDto.getCalories()
                )));
    }
}


