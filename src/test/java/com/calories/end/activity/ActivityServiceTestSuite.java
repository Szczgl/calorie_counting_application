package com.calories.end.activity;

import com.calories.end.domain.Activity;
import com.calories.end.domain.User;
import com.calories.end.dto.ActivityDTO;
import com.calories.end.exception.ActivityNotFoundException;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.ActivityMapper;
import com.calories.end.repository.ActivityRepository;
import com.calories.end.repository.UserRepository;
import com.calories.end.services.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ActivityServiceTestSuite {

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private ActivityMapper activityMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ActivityService activityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetAllActivities() {
        // GIVEN
        Activity activity = new Activity();
        ActivityDTO activityDTO = new ActivityDTO();
        when(activityRepository.findAll()).thenReturn(List.of(activity));
        when(activityMapper.mapToActivityDto(any(Activity.class))).thenReturn(activityDTO);

        // WHEN
        List<ActivityDTO> result = activityService.getAllActivities();

        // THEN
        assertEquals(1, result.size());
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    void testGetActivityById() throws ActivityNotFoundException {
        // GIVEN
        Activity activity = new Activity();
        ActivityDTO activityDTO = new ActivityDTO();
        when(activityRepository.findById(anyLong())).thenReturn(Optional.of(activity));
        when(activityMapper.mapToActivityDto(any(Activity.class))).thenReturn(activityDTO);

        // WHEN
        ActivityDTO result = activityService.getActivityById(1L);

        // THEN
        assertNotNull(result);
        verify(activityRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveActivity() throws UserNotFoundException {
        // GIVEN
        Activity activity = new Activity();
        ActivityDTO activityDTO = new ActivityDTO();
        User user = new User();
        activityDTO.setUserId(1L);
        when(activityMapper.mapToActivity(any(ActivityDTO.class))).thenReturn(activity);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        // WHEN
        Activity result = activityService.saveActivity(activityDTO);

        // THEN
        assertNotNull(result);
        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void testDeleteActivityById() throws ActivityNotFoundException {
        // WHEN
        activityService.deleteActivityById(1L);

        // THEN
        verify(activityRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testUpdateActivity() throws ActivityNotFoundException, UserNotFoundException {
        // GIVEN
        Activity activity = new Activity();
        ActivityDTO activityDTO = new ActivityDTO();
        User user = new User();
        activityDTO.setUserId(1L);
        when(activityRepository.existsById(anyLong())).thenReturn(true);
        when(activityMapper.mapToActivity(any(ActivityDTO.class))).thenReturn(activity);
        when(activityRepository.save(any(Activity.class))).thenReturn(activity);
        when(activityMapper.mapToActivityDto(any(Activity.class))).thenReturn(activityDTO);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // WHEN
        ActivityDTO result = activityService.replaceActivity(activityDTO, 1L);

        // THEN
        assertNotNull(result);
        verify(activityRepository, times(1)).existsById(anyLong());
        verify(activityRepository, times(1)).save(any(Activity.class));
    }

    @Test
    void testSaveActivityUserNotFound() {
        // GIVEN
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setUserId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> activityService.saveActivity(activityDTO));
        verify(userRepository, times(1)).findById(anyLong());
        verify(activityRepository, never()).save(any(Activity.class));
    }

    @Test
    void testActivityNotFoundExceptionMessage() {
        // GIVEN
        String message = "Activity not found";

        // WHEN
        ActivityNotFoundException exception = new ActivityNotFoundException(message);

        // THEN
        assertEquals(message, exception.getMessage());
    }
}
