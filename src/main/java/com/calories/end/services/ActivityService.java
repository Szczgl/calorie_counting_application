package com.calories.end.services;

import com.calories.end.domain.Activity;
import com.calories.end.domain.User;
import com.calories.end.dto.ActivityDTO;
import com.calories.end.exception.ActivityNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.ActivityMapper;
import com.calories.end.repository.ActivityRepository;
import com.calories.end.repository.UserRepository;
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

    public Activity saveActivity(ActivityDTO activityDTO) throws UserNotFoundException {
        Activity activity = activityMapper.mapToActivity(activityDTO);
        User user = userRepository.findById(activityDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found" + activityDTO.getUserId()));
        activity.setUser(user);
        return activityRepository.save(activity);
    }

    public void deleteActivityById(Long id) throws ActivityNotFoundException {
        activityRepository.deleteById(id);
    }

    public ActivityDTO replaceActivity(ActivityDTO activityDTO, Long id) throws ActivityNotFoundException, UserNotFoundException {
        if (!activityRepository.existsById(id)) {
            throw new ActivityNotFoundException("Activity not found with id: " + id);
        }
        deleteActivityById(id);
        activityDTO.setId(null);

        Activity newActivity = saveActivity(activityDTO);
        return activityMapper.mapToActivityDto(newActivity);
    }

}
