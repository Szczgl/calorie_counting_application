package com.calories.end.user;

import com.calories.end.dto.UserDTO;
import com.calories.end.services.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllUsers() throws Exception {
        // GIVEN
        UserDTO userDTO1 = new UserDTO();
        UserDTO userDTO2 = new UserDTO();
        List<UserDTO> userDTOList = Arrays.asList(userDTO1, userDTO2);
        when(userService.getAllUsers()).thenReturn(userDTOList);

        // WHEN & THEN
        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        // GIVEN
        UserDTO userDTO = new UserDTO(1L, "TestUser", "test@example.com", 2000,1500);
        when(userService.getUserById(1L)).thenReturn(userDTO);

        // WHEN & THEN
        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("TestUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.dailyCalorieIntake").value(2000))
                .andExpect(jsonPath("$.dailyCalorieConsumption").value(1500));

        verify(userService, times(1)).getUserById(anyLong());
    }

    @Test
    void testCreateUser() throws Exception {
        // GIVEN
        UserDTO userDTO = new UserDTO(null, "TestUser", "test@example.com", 2000,1500);
        UserDTO savedUserDTO = new UserDTO(1L, "TestUser", "test@example.com", 2000,1500);
        when(userService.saveUser(any(UserDTO.class))).thenReturn(savedUserDTO);
        Gson gson = new Gson();
        String json = gson.toJson(userDTO);

        // WHEN & THEN
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("TestUser"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.dailyCalorieIntake").value(2000))
                .andExpect(jsonPath("$.dailyCalorieConsumption").value(1500));

        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }

    @Test
    void testUpdateUser() throws Exception {
        // GIVEN
        Long id = 1L;
        UserDTO userDTO = new UserDTO(id, "UpdatedUser", "updated@example.com", 2500,1500);
        when(userService.updateUser(any(UserDTO.class), anyLong())).thenReturn(userDTO);
        Gson gson = new Gson();
        String json = gson.toJson(userDTO);

        // WHEN & THEN
        mockMvc.perform(put("/v1/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.username").value("UpdatedUser"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.dailyCalorieIntake").value(2500))
                .andExpect(jsonPath("$.dailyCalorieConsumption").value(1500));

        verify(userService, times(1)).updateUser(any(UserDTO.class), eq(id));
    }

    @Test
    void testDeleteUser() throws Exception {
        // WHEN & THEN
        mockMvc.perform(delete("/v1/users/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(anyLong());
    }
}