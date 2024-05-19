package com.calories.end.mapper;


import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {

    public IngredientDTO toDto(Ingredient ingredient) {
        return new IngredientDTO();
    }

    public Ingredient toEntity(IngredientDTO ingredientDTO) {
        return new Ingredient();
    }
}
