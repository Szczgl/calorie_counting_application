package com.calories.end.user;

import com.calories.end.domain.User;
import com.calories.end.dto.UserDTO;
import com.calories.end.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void testMapToUserDto() {
        // GIVEN
        User user = new User(1L, "JohnDoe", "john.doe@example.com", 2000);

        // WHEN
        UserDTO userDto = userMapper.mapToUserDto(user);

        // THEN
        assertEquals(1L, userDto.getId());
        assertEquals("JohnDoe", userDto.getUsername());
        assertEquals("john.doe@example.com", userDto.getEmail());
        assertEquals(2000, userDto.getDailyCalorieIntake());
    }

    @Test
    void testMapToUser() {
        // GIVEN
        UserDTO userDto = new UserDTO(1L, "JohnDoe", "john.doe@example.com", 2000);

        // WHEN
        User user = userMapper.mapToUser(userDto);

        // THEN
        assertEquals(1L, user.getId());
        assertEquals("JohnDoe", user.getUsername());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals(2000, user.getDailyCalorieIntake());
    }

    @Test
    void testMapToUserDtoList() {
        // GIVEN
        List<User> users = Arrays.asList(
                new User(1L, "JohnDoe", "john.doe@example.com", 2000),
                new User(2L, "JaneDoe", "jane.doe@example.com", 1800)
        );

        // WHEN
        List<UserDTO> userDtos = userMapper.mapToUserDtoList(users);

        // THEN
        assertEquals(2, userDtos.size());
    }
}
