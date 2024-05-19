package com.calories.end.services;

import com.calories.end.domain.Ingredient;
import com.calories.end.dto.IngredientDTO;
import com.calories.end.exception.IngredientNotFoundException;
import com.calories.end.mapper.IngredientMapper;
import com.calories.end.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                .map(ingredientMapper::mapToIngredientDto)
                .collect(Collectors.toList());
    }

    public IngredientDTO getIngredientById(Long id) throws IngredientNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));
        return ingredientMapper.mapToIngredientDto(ingredient);
    }

    public IngredientDTO saveIngredient(IngredientDTO ingredientDto) {
        Ingredient ingredient = ingredientMapper.mapToIngredient(ingredientDto);
        return ingredientMapper.mapToIngredientDto(ingredientRepository.save(ingredient));
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    public IngredientDTO updateIngredient(IngredientDTO ingredientDto, Long id) throws IngredientNotFoundException {
        if (!ingredientRepository.existsById(id)) {
            throw new IngredientNotFoundException("Ingredient not found with id: " + id);
        }
        ingredientDto.setId(id);
        Ingredient ingredient = ingredientMapper.mapToIngredient(ingredientDto);
        return ingredientMapper.mapToIngredientDto(ingredientRepository.save(ingredient));
    }
}
