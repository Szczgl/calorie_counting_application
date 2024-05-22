
package com.calories.end.recipe;

import com.calories.end.domain.Recipe;
import com.calories.end.dto.RecipeDTO;
import com.calories.end.mapper.RecipeMapper;
import com.calories.end.services.RecipeService;
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
class RecipeControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private RecipeMapper recipeMapper;

    @Test
    void testGetAllRecipes() throws Exception {
        // GIVEN
        RecipeDTO recipeDTO1 = new RecipeDTO();
        RecipeDTO recipeDTO2 = new RecipeDTO();
        List<RecipeDTO> recipeDTOList = Arrays.asList(recipeDTO1, recipeDTO2);
        when(recipeService.getAllRecipes()).thenReturn(recipeDTOList);

        // WHEN & THEN
        mockMvc.perform(get("/v1/recipes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(recipeService, times(1)).getAllRecipes();
    }

    @Test
    void testGetRecipeById() throws Exception {
        // GIVEN
        RecipeDTO recipeDTO = new RecipeDTO(1L,"Test1","Test2", 100,"Test");
        when(recipeService.getRecipeById(1L)).thenReturn(recipeDTO);

        // WHEN & THEN
        mockMvc.perform(get("/v1/recipes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.description").value("Test2"))
                .andExpect(jsonPath("$.totalCalories").value(100))
                .andExpect(jsonPath("$.translatedDescription").value("Test"));

        verify(recipeService, times(1)).getRecipeById(anyLong());
    }

    @Test
    void testCreateRecipe() throws Exception {
        // GIVEN
        RecipeDTO recipeDTO = new RecipeDTO(null,"Test1","Test2", 100,"Test", 1L, null);
        Recipe recipe = new Recipe(1L,"Test1","Test2", 100,"Test", null, null);
        when(recipeMapper.mapToRecipe(any(RecipeDTO.class))).thenReturn(recipe);
        when(recipeService.saveRecipe(any(RecipeDTO.class))).thenReturn(recipe);
        when(recipeMapper.mapToRecipeDto(any(Recipe.class))).thenReturn(recipeDTO);
        Gson gson = new Gson();
        String json = gson.toJson(recipeDTO);

        // WHEN & THEN
        mockMvc.perform(post("/v1/recipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test1"))
                .andExpect(jsonPath("$.description").value("Test2"))
                .andExpect(jsonPath("$.totalCalories").value(100))
                .andExpect(jsonPath("$.translatedDescription").value("Test"));

        verify(recipeService, times(1)).saveRecipe(any(RecipeDTO.class));
    }

    @Test
    void testUpdateRecipe() throws Exception {
        // GIVEN
        Long id = 1L;
        RecipeDTO recipeDTO = new RecipeDTO(id, "UpdatedName", "UpdatedDescription", 200, "UpdatedTranslation", 1L, null);
        Recipe recipe = new Recipe(id, "UpdatedName", "UpdatedDescription", 200, "UpdatedTranslation", null, null);
        when(recipeMapper.mapToRecipe(any(RecipeDTO.class))).thenReturn(recipe);
        when(recipeService.updateRecipe(any(RecipeDTO.class), anyLong())).thenReturn(recipeDTO);
        when(recipeMapper.mapToRecipeDto(any(Recipe.class))).thenReturn(recipeDTO);
        Gson gson = new Gson();
        String json = gson.toJson(recipeDTO);

        // WHEN & THEN
        mockMvc.perform(put("/v1/recipes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.description").value("UpdatedDescription"))
                .andExpect(jsonPath("$.totalCalories").value(200))
                .andExpect(jsonPath("$.translatedDescription").value("UpdatedTranslation"));

        verify(recipeService, times(1)).updateRecipe(any(RecipeDTO.class), eq(id));
    }

    @Test
    void testDeleteRecipe() throws Exception {
        // WHEN & THEN
        mockMvc.perform(delete("/v1/recipes/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(recipeService, times(1)).deleteRecipe(anyLong());
    }
}