package com.calories.end.controller;

import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.exception.IngredientNotFoundException;
import com.calories.end.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<IngredientDTO>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDTO> getIngredientById(@PathVariable Long id) throws IngredientNotFoundException {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientDTO> updateIngredient(@RequestBody IngredientDTO ingredientDto, @PathVariable Long id) throws IngredientNotFoundException {
        return ResponseEntity.ok(ingredientService.updateIngredient(ingredientDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/is-in-recipe")
    public ResponseEntity<Boolean> isIngredientInAnyRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.isIngredientInAnyRecipe(id));
    }

    @GetMapping("/existsByName")
    public boolean existsByName(@RequestParam String name) {
        return ingredientService.existsByName(name);
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody IngredientDTO ingredientDTO) {
        if (ingredientService.existsByName(ingredientDTO.getName())) {
            return ResponseEntity.badRequest().build();
        }
        Ingredient newIngredient = ingredientService.createIngredient(ingredientDTO);
        return ResponseEntity.ok(newIngredient);
    }
}

