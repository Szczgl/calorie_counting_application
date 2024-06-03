package com.calories.end.mapper;

import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientMapper {

    public IngredientDTO mapToIngredientDto(Ingredient ingredient) {
        return new IngredientDTO(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getCalories()
        );
    }

    public Ingredient mapToIngredient(IngredientDTO ingredientDto) {
        return new Ingredient(
                ingredientDto.getId(),
                ingredientDto.getName(),
                ingredientDto.getQuantity(),
                ingredientDto.getCalories()
        );
    }

    public List<IngredientDTO> mapToIngredientDtoList(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(this::mapToIngredientDto)
                .collect(Collectors.toList());
    }

    public List<Ingredient> mapToIngredientList(List<IngredientDTO> ingredientDtos) {
        return ingredientDtos.stream()
                .map(this::mapToIngredient)
                .collect(Collectors.toList());
    }
}
