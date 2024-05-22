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
        IngredientDTO ingredientDTO = new IngredientDTO(1L, "Test1", "100g", 100, 10, 1, 0);
        when(ingredientService.getIngredientById(1L)).thenReturn(ingredientDTO);

        // WHEN & THEN
        mockMvc.perform(get("/v1/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.quantity").value("100g"))
                .andExpect(jsonPath("$.calories").value(100))
                .andExpect(jsonPath("$.sugar").value(10))
                .andExpect(jsonPath("$.salt").value(1))
                .andExpect(jsonPath("$.cholesterol").value(0));

        verify(ingredientService, times(1)).getIngredientById(anyLong());
    }

    @Test
    void testCreateIngredient() throws Exception {
        // GIVEN
        IngredientDTO ingredientDTO = new IngredientDTO(null, "Test1", "100g", 100, 10, 1, 0);
        IngredientDTO savedIngredientDTO = new IngredientDTO(1L, "Test1", "100g", 100, 10, 1, 0);
        when(ingredientService.saveIngredient(any(IngredientDTO.class))).thenReturn(savedIngredientDTO);
        Gson gson = new Gson();
        String json = gson.toJson(ingredientDTO);

        // WHEN & THEN
        mockMvc.perform(post("/v1/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.quantity").value("100g"))
                .andExpect(jsonPath("$.calories").value(100))
                .andExpect(jsonPath("$.sugar").value(10))
                .andExpect(jsonPath("$.salt").value(1))
                .andExpect(jsonPath("$.cholesterol").value(0));

        verify(ingredientService, times(1)).saveIngredient(any(IngredientDTO.class));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        // GIVEN
        Long id = 1L;
        IngredientDTO ingredientDTO = new IngredientDTO(id, "UpdatedName", "200g", 200, 20, 2, 1);
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
                .andExpect(jsonPath("$.quantity").value("200g"))
                .andExpect(jsonPath("$.calories").value(200))
                .andExpect(jsonPath("$.sugar").value(20))
                .andExpect(jsonPath("$.salt").value(2))
                .andExpect(jsonPath("$.cholesterol").value(1));

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