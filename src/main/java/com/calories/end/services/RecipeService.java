package com.calories.end.services;

import com.calories.end.domain.Ingredient;
import com.calories.end.domain.Recipe;
import com.calories.end.domain.User;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.exception.RecipeNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.RecipeMapper;
import com.calories.end.repository.RecipeRepository;
import com.calories.end.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    @PersistenceContext
    private EntityManager entityManager;

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;
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

    public Recipe saveRecipe(RecipeDTO recipeDto) throws UserNotFoundException {
        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
        User user = userRepository.findById(recipeDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + recipeDto.getUserId()));
        recipe.setUser(user);
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public RecipeDTO updateRecipe(RecipeDTO recipeDto, Long id) throws RecipeNotFoundException {
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("Recipe not found with id: " + id);
        }
        recipeDto.setId(id);
        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
        return recipeMapper.mapToRecipeDto(recipeRepository.save(recipe));
    }
}

