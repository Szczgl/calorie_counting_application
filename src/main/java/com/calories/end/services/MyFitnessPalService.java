package com.calories.end.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyFitnessPalService {

    private final RestTemplate restTemplate;

    @Value("${myfitnesspal.api.url}")
    private String apiUrl;

    @Value("${myfitnesspal.api.key}")
    private String apiKey;

    @Value("${myfitnesspal.api.host}")
    private String apiHost;

    public MyFitnessPalService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", apiKey);
        headers.set("X-RapidAPI-Host", apiHost);


        return headers;
    }

    public String getyFitnessPal(String keyword) {
        HttpEntity<String> entity = new HttpEntity<>(createHeaders());
        ResponseEntity<String> response = restTemplate.exchange(apiUrl + "/searchByKeyword?keyword=" + keyword, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }
}
