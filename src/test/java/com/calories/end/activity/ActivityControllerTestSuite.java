package com.calories.end.activity;

import com.calories.end.domain.Activity;
import com.calories.end.dto.ActivityDTO;
import com.calories.end.mapper.ActivityMapper;
import com.calories.end.services.ActivityService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerTestSuite {

    @MockBean
    private ActivityService activityService;

    @MockBean
    private ActivityMapper activityMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllActivities() throws Exception {
        // GIVEN
        List<ActivityDTO> activityDTOList = List.of(new ActivityDTO(), new ActivityDTO());
        when(activityService.getAllActivities()).thenReturn(activityDTOList);

        // WHEN & THEN
        mockMvc.perform(get("/v1/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

        verify(activityService, times(1)).getAllActivities();
    }

    @Test
    void testGetActivityById() throws Exception {
        // GIVEN
        ActivityDTO activityDTO = new ActivityDTO(1L, "TestName", "TestDescription", 100);
        when(activityService.getActivityById(1L)).thenReturn(activityDTO);

        // WHEN & THEN
        mockMvc.perform(get("/v1/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.description").value("TestDescription"))
                .andExpect(jsonPath("$.consumedCalories").value(100));

        verify(activityService, times(1)).getActivityById(1L);
    }

    @Test
    void testCreateActivity() throws Exception {
        // GIVEN
        ActivityDTO activityDTO = new ActivityDTO(1L, "TestName", "TestDescription", 100);
        Activity activity = new Activity(1L, "TestName", "TestDescription", 100, null);
        when(activityMapper.mapToActivity(any(ActivityDTO.class))).thenReturn(activity);
        when(activityService.saveActivity(any(ActivityDTO.class))).thenReturn(activity);
        when(activityMapper.mapToActivityDto(any(Activity.class))).thenReturn(activityDTO);
        Gson gson = new Gson();
        String json = gson.toJson(activityDTO);

        // WHEN & THEN
        mockMvc.perform(post("/v1/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("TestName"))
                .andExpect(jsonPath("$.description").value("TestDescription"))
                .andExpect(jsonPath("$.consumedCalories").value(100));

        verify(activityService, times(1)).saveActivity(any(ActivityDTO.class));
    }

    @Test
    void testUpdateActivity() throws Exception {
        // GIVEN
        Long id = 1L;
        ActivityDTO activityDTO = new ActivityDTO(id, "UpdatedName", "UpdatedDescription", 200);
        Activity activity = new Activity(id, "UpdatedName", "UpdatedDescription", 200, null);
        when(activityMapper.mapToActivity(any(ActivityDTO.class))).thenReturn(activity);
        when(activityService.replaceActivity(any(ActivityDTO.class), anyLong())).thenReturn(activityDTO);
        when(activityMapper.mapToActivityDto(any(Activity.class))).thenReturn(activityDTO);
        Gson gson = new Gson();
        String json = gson.toJson(activityDTO);

        // WHEN & THEN
        mockMvc.perform(put("/v1/activities/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("UpdatedName"))
                .andExpect(jsonPath("$.description").value("UpdatedDescription"))
                .andExpect(jsonPath("$.consumedCalories").value(200));

        verify(activityService, times(1)).replaceActivity(any(ActivityDTO.class), eq(id));
    }

    @Test
    void testDeleteActivity() throws Exception {
        // WHEN & THEN
        mockMvc.perform(delete("/v1/activities/{id}", 1L))
                .andExpect(status().isOk());

        verify(activityService, times(1)).deleteActivityById(anyLong());
    }
}