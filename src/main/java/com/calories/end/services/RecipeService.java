package com.calories.end.services;

import com.calories.end.domain.Recipe;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.mapper.RecipeMapper;
import com.calories.end.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeMapper recipeMapper;

    public List<RecipeDTO> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipeMapper::toDto)
                .collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(Long id) {
        return recipeMapper.toDto(recipeRepository.findById(id).orElseThrow());
    }

    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        return recipeMapper.toDto(recipeRepository.save(recipeMapper.toEntity(recipeDTO)));
    }

    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        return recipeRepository.findById(id)
                .map(existingRecipe -> {
                    Recipe updatedRecipe = recipeMapper.toEntity(recipeDTO);
                    updatedRecipe.setId(existingRecipe.getId());
                    return recipeMapper.toDto(recipeRepository.save(updatedRecipe));
                })
                .orElseThrow();
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}

