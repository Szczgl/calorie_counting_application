package com.calories.end.controller;

import com.calories.end.dto.IngredientDTO;
import com.calories.end.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public List<IngredientDTO> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @PostMapping
    public IngredientDTO addIngredient(@RequestBody IngredientDTO ingredientDTO) {
        return ingredientService.addIngredient(ingredientDTO);
    }

    @PutMapping("/{id}")
    public IngredientDTO updateIngredient(@PathVariable Long id, @RequestBody IngredientDTO ingredientDTO) {
        return ingredientService.updateIngredient(id, ingredientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
    }
}

