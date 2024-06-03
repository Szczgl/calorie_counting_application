package com.calories.end.activity;

import com.calories.end.domain.Activity;
import com.calories.end.domain.User;
import com.calories.end.dto.ActivityDTO;
import com.calories.end.mapper.ActivityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ActivityMapperTestSuite {

    @InjectMocks
    private ActivityMapper activityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapToActivityDto() {
        // GIVEN
        User user = new User(1L, "Test", "test@test.com", 2000,1500);
        Set<User> users = new HashSet<>();
        users.add(user);
        Activity activity = new Activity(1L, "TestName", "TestDescription", 100, "Test2");
        activity.setUser(user);

        // WHEN
        ActivityDTO activityDTO = activityMapper.mapToActivityDto(activity);

        // THEN
        assertEquals(1L, activityDTO.getId());
        assertEquals("TestName", activityDTO.getName());
        assertEquals("TestDescription", activityDTO.getDescription());
        assertEquals(100, activityDTO.getConsumedCalories());
    }
}
