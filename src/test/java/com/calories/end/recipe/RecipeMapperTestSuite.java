package com.calories.end.recipe;

import com.calories.end.domain.Ingredient;
import com.calories.end.domain.Recipe;
import com.calories.end.domain.User;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.mapper.RecipeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;

class RecipeMapperTestSuite {

    @InjectMocks
    private RecipeMapper recipeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToRecipeDto() {
        // GIVEN
        User user = new User(1L, "Test", "test@test.com", 2000);
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient(1L, "Sugar", 200, 400, 100, 0, 0));

        Recipe recipe = new Recipe(1L, "Pancakes", "Delicious pancakes", 600, "Translated description");
        recipe.setUser(user);
        recipe.setIngredients(ingredients);

        // WHEN
        RecipeDTO recipeDto = recipeMapper.mapToRecipeDto(recipe);

        // THEN
        assertEquals(1L, recipeDto.getId());
        assertEquals("Pancakes", recipeDto.getName());
        assertEquals("Delicious pancakes", recipeDto.getDescription());
        assertEquals(600, recipeDto.getTotalCalories());
        assertEquals("Translated description", recipeDto.getTranslatedDescription());
        assertEquals(1L, recipeDto.getUserId());
        assertEquals(1, recipeDto.getIngredientIds().size());
        assertTrue(recipeDto.getIngredientIds().contains(1L));
    }
}