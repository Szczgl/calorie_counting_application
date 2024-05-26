package com.calories.end.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExerciseService {

    private final RestTemplate restTemplate;

    @Value("${exercisedb.api.url}")
    private String apiUrl;

    @Value("${exercisedb.api.key}")
    private String apiKey;

    @Value("${exercisedb.api.host}")
    private String apiHost;

    public ExerciseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);
        return headers;
    }

    public String getExercises() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getExerciseByName(String name) {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/name/" + name, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getExerciseById(String id) {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/exercise/" + id, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getExercisesByEquipment(String type) {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/equipment/" + type, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getExercisesByTarget(String target) {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/target/" + target, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getTargetList() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/targetList", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getEquipmentList() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/equipmentList", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getBodyPartList() {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/bodyPartList", HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String getExercisesByBodyPart(String bodyPart) {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/exercises/bodyPart/" + bodyPart, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
