package com.calories.end.controller;

import com.calories.end.domain.Activity;
import com.calories.end.dto.ActivityDTO;
import com.calories.end.exception.ActivityNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.ActivityMapper;
import com.calories.end.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityMapper activityMapper;
    private final ActivityService activityService;

    @GetMapping
    public ResponseEntity<List<ActivityDTO>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) throws ActivityNotFoundException {
        return ResponseEntity.ok(activityService.getActivityById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDTO> createActivity(@RequestBody ActivityDTO activityDTO) throws UserNotFoundException {
        Activity savedActivity = activityService.saveActivity(activityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(activityMapper.mapToActivityDto(savedActivity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(@RequestBody ActivityDTO activityDTO, @PathVariable Long id) throws ActivityNotFoundException, UserNotFoundException {
        return ResponseEntity.ok(activityService.replaceActivity(activityDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ActivityDTO> deleteActivity(@PathVariable Long id) throws ActivityNotFoundException {
        activityService.deleteActivityById(id);
        return ResponseEntity.ok().build();
    }
}
