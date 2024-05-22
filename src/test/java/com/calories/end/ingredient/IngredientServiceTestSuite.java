package com.calories.end.ingredient;

import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.exception.IngredientNotFoundException;
import com.calories.end.mapper.IngredientMapper;
import com.calories.end.repository.IngredientRepository;
import com.calories.end.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTestSuite {

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientMapper ingredientMapper;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    void testGetAllIngredients() {
        // GIVEN
        Ingredient ingredient = new Ingredient();
        IngredientDTO ingredientDTO = new IngredientDTO();
        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient));
        when(ingredientMapper.mapToIngredientDto(any(Ingredient.class))).thenReturn(ingredientDTO);

        // WHEN
        List<IngredientDTO> result = ingredientService.getAllIngredients();

        // THEN
        assertEquals(1, result.size());
        verify(ingredientRepository, times(1)).findAll();
    }

    @Test
    void testGetIngredientById() throws IngredientNotFoundException {
        // GIVEN
        Ingredient ingredient = new Ingredient();
        IngredientDTO ingredientDTO = new IngredientDTO();
        when(ingredientRepository.findById(anyLong())).thenReturn(Optional.of(ingredient));
        when(ingredientMapper.mapToIngredientDto(any(Ingredient.class))).thenReturn(ingredientDTO);

        // WHEN
        IngredientDTO result = ingredientService.getIngredientById(1L);

        // THEN
        assertNotNull(result);
        verify(ingredientRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveIngredient() {
        // GIVEN
        Ingredient ingredient = new Ingredient();
        IngredientDTO ingredientDTO = new IngredientDTO();
        when(ingredientMapper.mapToIngredient(any(IngredientDTO.class))).thenReturn(ingredient);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);
        when(ingredientMapper.mapToIngredientDto(any(Ingredient.class))).thenReturn(ingredientDTO);

        // WHEN
        IngredientDTO result = ingredientService.saveIngredient(ingredientDTO);

        // THEN
        assertNotNull(result);
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    void testDeleteIngredient() {
        // WHEN
        ingredientService.deleteIngredient(1L);

        // THEN
        verify(ingredientRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testUpdateIngredient() throws IngredientNotFoundException {
        // GIVEN
        Ingredient ingredient = new Ingredient();
        IngredientDTO ingredientDTO = new IngredientDTO();
        when(ingredientRepository.existsById(anyLong())).thenReturn(true);
        when(ingredientMapper.mapToIngredient(any(IngredientDTO.class))).thenReturn(ingredient);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);
        when(ingredientMapper.mapToIngredientDto(any(Ingredient.class))).thenReturn(ingredientDTO);

        // WHEN
        IngredientDTO result = ingredientService.updateIngredient(ingredientDTO, 1L);

        // THEN
        assertNotNull(result);
        verify(ingredientRepository, times(1)).existsById(anyLong());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    void testUpdateIngredientNotFound() {
        // GIVEN
        IngredientDTO ingredientDTO = new IngredientDTO();
        when(ingredientRepository.existsById(anyLong())).thenReturn(false);

        // WHEN & THEN
        assertThrows(IngredientNotFoundException.class, () -> ingredientService.updateIngredient(ingredientDTO, 1L));
        verify(ingredientRepository, times(1)).existsById(anyLong());
        verify(ingredientRepository, never()).save(any(Ingredient.class));
    }
}