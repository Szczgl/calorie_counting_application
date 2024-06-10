package com.calories.end.services;

import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.exception.IngredientNotFoundException;
import com.calories.end.mapper.IngredientMapper;
import com.calories.end.repository.IngredientRepository;
import com.calories.end.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final RecipeRepository recipeRepository;

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::mapToIngredientDto)
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(Long id) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));
        return ingredientMapper.mapToIngredientDto(ingredient);
    }

    public IngredientDTO saveIngredient(IngredientDTO ingredientDto) {
        Ingredient ingredient = ingredientMapper.mapToIngredient(ingredientDto);
        return ingredientMapper.mapToIngredientDto(ingredientRepository.save(ingredient));
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    public IngredientDTO updateIngredient(IngredientDTO ingredientDto, Long id) throws IngredientNotFoundException {
        if (!ingredientRepository.existsById(id)) {
            throw new IngredientNotFoundException("Ingredient not found with id: " + id);
        }
        ingredientDto.setId(id);
        Ingredient ingredient = ingredientMapper.mapToIngredient(ingredientDto);
        return ingredientMapper.mapToIngredientDto(ingredientRepository.save(ingredient));
    }

    public boolean isIngredientInAnyRecipe(Long ingredientId) {
        return recipeRepository.existsByIngredients_Id(ingredientId);
    }

    public boolean existsByName(String name) {
        return ingredientRepository.existsByName(name);
    }

    public Ingredient createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientDTO.getName());
        ingredient.setQuantity(ingredientDTO.getQuantity());
        ingredient.setCalories(ingredientDTO.getCalories());
        return ingredientRepository.save(ingredient);
    }
}
