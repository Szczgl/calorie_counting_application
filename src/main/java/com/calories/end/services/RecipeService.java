package com.calories.end.services;

import com.calories.end.domain.Recipe;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.exception.RecipeNotFoundException;
import com.calories.end.mapper.RecipeMapper;
import com.calories.end.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

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

    public RecipeDTO saveRecipe(RecipeDTO recipeDto) {
        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);
        return recipeMapper.mapToRecipeDto(recipeRepository.save(recipe));
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

