package com.calories.end.services;

import com.calories.end.domain.Activity;
import com.calories.end.domain.User;
import com.calories.end.dto.ActivityDTO;
import com.calories.end.exception.ActivityNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.ActivityMapper;
import com.calories.end.repository.ActivityRepository;
import com.calories.end.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserRepository userRepository;
    private final ExerciseService exerciseService;

    public List<ActivityDTO> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activityMapper::mapToActivityDto)
                .collect(Collectors.toList());
    }

    public ActivityDTO getActivityById(Long id) throws ActivityNotFoundException {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found" + id));
        return activityMapper.mapToActivityDto(activity);
    }

    public Activity saveActivity(ActivityDTO activityDTO) throws UserNotFoundException, ActivityNotFoundException, JsonProcessingException {
        if ("api".equalsIgnoreCase(activityDTO.getSource())) {
            String exerciseData = exerciseService.getExerciseByName(activityDTO.getName());
            if (exerciseData != null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(exerciseData);
                if (root.isArray() && root.size() > 0) {
                    JsonNode exercise = root.get(0);
                    activityDTO.setName(exercise.path("name").asText());
                    StringBuilder description = new StringBuilder();
                    exercise.path("instructions").forEach(instruction -> description.append(instruction.asText()).append("\n"));
                    activityDTO.setDescription(description.toString());
                } else {
                    throw new ActivityNotFoundException("Exercise not found in API");
                }
            } else {
                throw new ActivityNotFoundException("Exercise not found in API");
            }
        }
        Activity activity = activityMapper.mapToActivity(activityDTO);
        User user = userRepository.findById(activityDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found" + activityDTO.getUserId()));
        activity.setUser(user);
        return activityRepository.save(activity);
    }

    public ActivityDTO replaceActivity(ActivityDTO activityDTO, Long id) throws ActivityNotFoundException, UserNotFoundException, JsonProcessingException {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: " + id));

        existingActivity.setName(activityDTO.getName());
        existingActivity.setDescription(activityDTO.getDescription());
        existingActivity.setConsumedCalories(activityDTO.getConsumedCalories());
        existingActivity.setSource(activityDTO.getSource());
        User user = userRepository.findById(activityDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found" + activityDTO.getUserId()));
        existingActivity.setUser(user);
        Activity updatedActivity = activityRepository.save(existingActivity);
        return activityMapper.mapToActivityDto(updatedActivity);
    }

    public void deleteActivityById(Long id) throws ActivityNotFoundException {
        activityRepository.deleteById(id);
    }

}
