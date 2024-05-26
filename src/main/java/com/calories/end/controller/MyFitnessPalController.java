package com.calories.end.controller;

import com.calories.end.services.MyFitnessPalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fitness")
public class MyFitnessPalController {

    @Autowired
    private MyFitnessPalService myFitnessPalService;

    @GetMapping
    public String getMyFitnessPal(@RequestParam String keyword) {
        return myFitnessPalService.getyFitnessPal(keyword);
    }
}
