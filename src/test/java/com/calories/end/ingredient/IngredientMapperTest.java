package com.calories.end.ingredient;

import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.mapper.IngredientMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class IngredientMapperTest {

    private final IngredientMapper ingredientMapper = new IngredientMapper();

    @Test
    void testMapToIngredientDto() {
        // GIVEN
        Ingredient ingredient = new Ingredient(1L, "Sugar", 200, 400, 100, 0, 0);

        // WHEN
        IngredientDTO ingredientDto = ingredientMapper.mapToIngredientDto(ingredient);

        // THEN
        assertEquals(1L, ingredientDto.getId());
        assertEquals("Sugar", ingredientDto.getName());
        assertEquals(200, ingredientDto.getQuantity());
        assertEquals(400, ingredientDto.getCalories());
        assertEquals(100, ingredientDto.getSugar());
        assertEquals(0, ingredientDto.getSalt());
        assertEquals(0, ingredientDto.getCholesterol());
    }

    @Test
    void testMapToIngredient() {
        // GIVEN
        IngredientDTO ingredientDto = new IngredientDTO(1L, "Sugar", 200, 400, 100, 0, 0);

        // WHEN
        Ingredient ingredient = ingredientMapper.mapToIngredient(ingredientDto);

        // THEN
        assertEquals(1L, ingredient.getId());
        assertEquals("Sugar", ingredient.getName());
        assertEquals(200, ingredient.getQuantity());
        assertEquals(400, ingredient.getCalories());
        assertEquals(100, ingredient.getSugar());
        assertEquals(0, ingredient.getSalt());
        assertEquals(0, ingredient.getCholesterol());
    }

    @Test
    void testMapToIngredientDtoList() {
        // GIVEN
        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient(1L, "Sugar", 200, 400, 100, 0, 0),
                new Ingredient(2L, "Salt", 50, 0, 0, 50, 0)
        );

        // WHEN
        List<IngredientDTO> ingredientDtos = ingredientMapper.mapToIngredientDtoList(ingredients);

        // THEN
        assertEquals(2, ingredientDtos.size());
    }

    @Test
    void testMapToIngredientList() {
        // GIVEN
        List<IngredientDTO> ingredientDtos = Arrays.asList(
                new IngredientDTO(1L, "Sugar", 200, 400, 100, 0, 0),
                new IngredientDTO(2L, "Salt", 50, 0, 0, 50, 0)
        );

        // WHEN
        List<Ingredient> ingredients = ingredientMapper.mapToIngredientList(ingredientDtos);

        // THEN
        assertEquals(2, ingredients.size());
    }
}
