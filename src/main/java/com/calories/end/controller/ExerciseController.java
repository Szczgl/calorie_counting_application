package com.calories.end.controller;

import com.calories.end.services.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public String getExercises() {
        return exerciseService.getExercises();
    }

    @GetMapping("/name/{name}")
    public String getExerciseByName(@PathVariable String name) {
        return exerciseService.getExerciseByName(name);
    }

    @GetMapping("/exercise/{id}")
    public String getExerciseById(@PathVariable String id) {
        return exerciseService.getExerciseById(id);
    }

    @GetMapping("/equipment/{type}")
    public String getExercisesByEquipment(@PathVariable String type) {
        return exerciseService.getExercisesByEquipment(type);
    }

    @GetMapping("/target/{target}")
    public String getExercisesByTarget(@PathVariable String target) {
        return exerciseService.getExercisesByTarget(target);
    }

    @GetMapping("/targetList")
    public String getTargetList() {
        return exerciseService.getTargetList();
    }

    @GetMapping("/equipmentList")
    public String getEquipmentList() {
        return exerciseService.getEquipmentList();
    }

    @GetMapping("/bodyPartList")
    public String getBodyPartList() {
        return exerciseService.getBodyPartList();
    }

    @GetMapping("/bodyPart/{bodyPart}")
    public String getExercisesByBodyPart(@PathVariable String bodyPart) {
        return exerciseService.getExercisesByBodyPart(bodyPart);
    }
}
