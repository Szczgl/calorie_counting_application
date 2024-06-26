package com.calories.end.ingredient;

import com.calories.end.dto.IngredientDTO;
import com.calories.end.services.IngredientService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class IngredientControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @Test
    void testGetAllIngredients() throws Exception {
        // GIVEN
        IngredientDTO ingredientDTO1 = new IngredientDTO();
        IngredientDTO ingredientDTO2 = new IngredientDTO();
        List<IngredientDTO> ingredientDTOList = Arrays.asList(ingredientDTO1, ingredientDTO2);
        when(ingredientService.getAllIngredients()).thenReturn(ingredientDTOList);

        // WHEN & THEN
        mockMvc.perform(get("/v1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(ingredientService, times(1)).getAllIngredients();
    }

    @Test
    void testGetIngredientById() throws Exception {
        // GIVEN
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Test1", 200, 100);
        when(ingredientService.getIngredientById(1L)).thenReturn(ingredientDTO);

        // WHEN & THEN
        mockMvc.perform(get("/v1/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.quantity").value(200))
                .andExpect(jsonPath("$.calories").value(100));

        verify(ingredientService, times(1)).getIngredientById(anyLong());
    }

    @Test
    void testUpdateIngredient() throws Exception {
        // GIVEN
        Long id = 1L;
        IngredientDTO ingredientDTO = new IngredientDTO(id, "UpdatedName", 200, 200);
        when(ingredientService.updateIngredient(any(IngredientDTO.class), anyLong())).thenReturn(ingredientDTO);
        Gson gson = new Gson();
        String json = gson.toJson(ingredientDTO);

        // WHEN & THEN
        mockMvc.perform(put("/v1/ingredients/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.quantity").value(200))
                .andExpect(jsonPath("$.calories").value(200));

        verify(ingredientService, times(1)).updateIngredient(any(IngredientDTO.class), eq(id));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        // WHEN & THEN
        mockMvc.perform(delete("/v1/ingredients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(ingredientService, times(1)).deleteIngredient(anyLong());
    }
}