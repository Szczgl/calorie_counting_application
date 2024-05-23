package com.calories.end.mapper;

import com.calories.end.domain.Activity;
import com.calories.end.dto.ActivityDTO;
import com.calories.end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityMapper {

    private final UserRepository userRepository;

    public ActivityDTO mapToActivityDto(Activity activity) {
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setId(activity.getId());
        activityDTO.setName(activity.getName());
        activityDTO.setDescription(activity.getDescription());
        activityDTO.setConsumedCalories(activity.getConsumedCalories());
        activityDTO.setUserId(activity.getId());

        return activityDTO;
    }

    public Activity mapToActivity(ActivityDTO activityDTO) {
        Activity activity = new Activity();
        activity.setId(activityDTO.getId());
        activity.setName(activityDTO.getName());
        activity.setDescription(activityDTO.getDescription());
        activity.setConsumedCalories(activityDTO.getConsumedCalories());

        return activity;
    }
}
