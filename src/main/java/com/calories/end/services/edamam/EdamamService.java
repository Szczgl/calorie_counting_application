package com.calories.end.services.edamam;

import com.calories.end.dto.IngredientDTO;
import com.calories.end.exception.IngredientNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EdamamService {

    private final RestTemplate restTemplate;

    @Value("${edamam.api.url}")
    private String apiUrl;

    @Value("${edamam.api.app_id}")
    private String appId;

    @Value("${edamam.api.app_key}")
    private String appKey;

    public EdamamService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public IngredientDTO searchIngredientByName(String name) throws IngredientNotFoundException {
        String url = apiUrl + "/parser?ingr=" + name + "&app_id=" + appId + "&app_key=" + appKey;
        ResponseEntity<EdamamResponse> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                EdamamResponse.class
        );

        EdamamResponse edamamResponse = response.getBody();
        if (edamamResponse != null && !edamamResponse.getParsed().isEmpty()) {
            EdamamFoodItem item = edamamResponse.getParsed().get(0).getFood();
            return new IngredientDTO(null, item.getLabel(), 100, item.getNutrients().getCalories());
        } else {
            throw new IngredientNotFoundException("Ingredient not found for name: " + name);
        }
    }
}