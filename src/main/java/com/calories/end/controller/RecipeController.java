package com.calories.end.controller;

import com.calories.end.dto.RecipeDTO;
import com.calories.end.exception.IngredientNotFoundException;
import com.calories.end.exception.RecipeNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathVariable Long id) throws RecipeNotFoundException {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDTO> createRecipe(@RequestBody RecipeDTO recipeDto) throws UserNotFoundException, IngredientNotFoundException {
        RecipeDTO savedRecipeDto = recipeService.saveRecipe(recipeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipeDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDto, @PathVariable Long id) throws RecipeNotFoundException, UserNotFoundException, IngredientNotFoundException {
        return ResponseEntity.ok(recipeService.replaceRecipe(recipeDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok().build();
    }
}

