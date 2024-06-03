package com.calories.end.user;

import com.calories.end.domain.User;
import com.calories.end.dto.UserDTO;
import com.calories.end.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class UserMapperTestSuite {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void testMapToUserDto() {
        // GIVEN
        User user = new User(1L, "Test", "test@test.com", 2000,1500);

        // WHEN
        UserDTO userDto = userMapper.mapToUserDto(user);

        // THEN
        assertEquals(1L, userDto.getId());
        assertEquals("Test", userDto.getUsername());
        assertEquals("test@test.com", userDto.getEmail());
        assertEquals(2000, userDto.getDailyCalorieIntake());
        assertEquals(1500,userDto.getDailyCalorieConsumption());
    }

    @Test
    void testMapToUser() {
        // GIVEN
        UserDTO userDto = new UserDTO(1L, "Test", "test@test.com", 2000,1500);

        // WHEN
        User user = userMapper.mapToUser(userDto);

        // THEN
        assertEquals(1L, user.getId());
        assertEquals("Test", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertEquals(2000, user.getDailyCalorieIntake());
        assertEquals(1500,user.getDailyCalorieConsumption());
    }

    @Test
    void testMapToUserDtoList() {
        // GIVEN
        List<User> users = Arrays.asList(
                new User(1L, "TestUser1", "test@test.com", 2000,1500),
                new User(2L, "TestUser2", "test2@test.com", 1800,1500)
        );

        // WHEN
        List<UserDTO> userDtos = userMapper.mapToUserDtoList(users);

        // THEN
        assertEquals(2, userDtos.size());
    }
}
