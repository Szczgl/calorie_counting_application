package com.calories.end.services;

import com.calories.end.domain.Ingredient;
import com.calories.end.domain.Recipe;
import com.calories.end.domain.User;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.exception.IngredientNotFoundException;
import com.calories.end.exception.RecipeNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.RecipeMapper;
import com.calories.end.repository.IngredientRepository;
import com.calories.end.repository.RecipeRepository;
import com.calories.end.repository.UserRepository;
import com.calories.end.services.edamam.EdamamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
    private final IngredientRepository ingredientRepository;
    private final UserRepository userRepository;
    private final EdamamService edamamService;

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
    public RecipeDTO saveRecipe(RecipeDTO recipeDto) throws UserNotFoundException, IngredientNotFoundException {
        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
        User user = userRepository.findById(recipeDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + recipeDto.getUserId()));
        recipe.setUser(user);

        Set<Ingredient> ingredients = recipeDto.getIngredients().stream()
                .map(ingredientDto -> {
                    try {
                        return findOrCreateIngredient(ingredientDto);
                    } catch (IngredientNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
        recipe.setIngredients(ingredients);

        recipe.setTotalCalories(ingredients.stream()
                .mapToDouble(Ingredient::getCalories)
                .sum());

        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.mapToRecipeDto(savedRecipe);
    }

    @Transactional
    public RecipeDTO replaceRecipe(RecipeDTO recipeDto, Long id) throws RecipeNotFoundException, UserNotFoundException, IngredientNotFoundException {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));

        existingRecipe.setName(recipeDto.getName());
        existingRecipe.setDescription(recipeDto.getDescription());

        User user = userRepository.findById(recipeDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + recipeDto.getUserId()));
        existingRecipe.setUser(user);

        Set<Ingredient> ingredients = recipeDto.getIngredients().stream()
                .map(ingredientDto -> {
                    try {
                        return findOrCreateIngredient(ingredientDto);
                    } catch (IngredientNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());
        existingRecipe.setIngredients(ingredients);

        existingRecipe.setTotalCalories(ingredients.stream()
                .mapToDouble(Ingredient::getCalories)
                .sum());

        Recipe savedRecipe = recipeRepository.save(existingRecipe);
        return recipeMapper.mapToRecipeDto(savedRecipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    private Ingredient findOrCreateIngredient(IngredientDTO ingredientDto) throws IngredientNotFoundException {
        Ingredient existingIngredient = ingredientRepository.findByName(ingredientDto.getName()).orElse(null);
        if (existingIngredient != null) {
            existingIngredient.setQuantity(existingIngredient.getQuantity() + ingredientDto.getQuantity());
            return existingIngredient;
        } else {
            IngredientDTO fetchedIngredient = edamamService.searchIngredientByName(ingredientDto.getName());
            return ingredientRepository.save(new Ingredient(
                    null,
                    fetchedIngredient.getName(),
                    ingredientDto.getQuantity(),
                    fetchedIngredient.getCalories() / fetchedIngredient.getQuantity() * ingredientDto.getQuantity()
            ));
        }
    }
}