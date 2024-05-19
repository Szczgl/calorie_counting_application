package com.calories.end.services;

import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.mapper.IngredientMapper;
import com.calories.end.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::toDto)
                .collect(Collectors.toList());
    }

    public IngredientDTO addIngredient(IngredientDTO ingredientDTO) {
        return ingredientMapper.toDto(ingredientRepository.save(ingredientMapper.toEntity(ingredientDTO)));
    }

    public IngredientDTO updateIngredient(Long id, IngredientDTO ingredientDTO) {
        return ingredientRepository.findById(id)
                .map(existingIngredient -> {
                    Ingredient updatedIngredient = ingredientMapper.toEntity(ingredientDTO);
                    updatedIngredient.setId(existingIngredient.getId());
                    return ingredientMapper.toDto(ingredientRepository.save(updatedIngredient));
                })
                .orElseThrow();
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }
}

