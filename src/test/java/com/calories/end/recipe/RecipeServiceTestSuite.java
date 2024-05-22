package com.calories.end.recipe;

import com.calories.end.domain.Recipe;
import com.calories.end.domain.User;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.exception.RecipeNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.RecipeMapper;
import com.calories.end.repository.RecipeRepository;
import com.calories.end.repository.UserRepository;
import com.calories.end.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class RecipeServiceTestSuite {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRecipes() {
        // GIVEN
        Recipe recipe = new Recipe();
        RecipeDTO recipeDTO = new RecipeDTO();
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));
        when(recipeMapper.mapToRecipeDto(any(Recipe.class))).thenReturn(recipeDTO);

        // WHEN
        List<RecipeDTO> result = recipeService.getAllRecipes();

        // THEN
        assertEquals(1, result.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testGetRecipeById() throws RecipeNotFoundException {
        // GIVEN
        Recipe recipe = new Recipe();
        RecipeDTO recipeDTO = new RecipeDTO();
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(recipeMapper.mapToRecipeDto(any(Recipe.class))).thenReturn(recipeDTO);

        // WHEN
        RecipeDTO result = recipeService.getRecipeById(1L);

        // THEN
        assertNotNull(result);
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveRecipe() throws UserNotFoundException {
        // GIVEN
        Recipe recipe = new Recipe();
        RecipeDTO recipeDTO = new RecipeDTO();
        User user = new User();
        recipeDTO.setUserId(1L);
        when(recipeMapper.mapToRecipe(any(RecipeDTO.class))).thenReturn(recipe);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        // WHEN
        Recipe result = recipeService.saveRecipe(recipeDTO);

        // THEN
        assertNotNull(result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testDeleteRecipe() {
        // WHEN
        recipeService.deleteRecipe(1L);

        // THEN
        verify(recipeRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testUpdateRecipe() throws RecipeNotFoundException {
        // GIVEN
        Recipe recipe = new Recipe();
        RecipeDTO recipeDTO = new RecipeDTO();
        when(recipeRepository.existsById(anyLong())).thenReturn(true);
        when(recipeMapper.mapToRecipe(any(RecipeDTO.class))).thenReturn(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(recipeMapper.mapToRecipeDto(any(Recipe.class))).thenReturn(recipeDTO);

        // WHEN
        RecipeDTO result = recipeService.updateRecipe(recipeDTO, 1L);

        // THEN
        assertNotNull(result);
        verify(recipeRepository, times(1)).existsById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testSaveRecipeUserNotFound() {
        // GIVEN
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setUserId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> recipeService.saveRecipe(recipeDTO));
        verify(userRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).save(any(Recipe.class));
    }

    @Test
    void testRecipeNotFoundExceptionMessage() {
        // GIVEN
        String message = "Recipe not found";

        // WHEN
        RecipeNotFoundException exception = new RecipeNotFoundException(message);

        // THEN
        assertEquals(message, exception.getMessage());
    }
}