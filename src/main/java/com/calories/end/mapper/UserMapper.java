package com.calories.end.mapper;

import com.calories.end.domain.User;
import com.calories.end.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserMapper {

    public UserDTO mapToUserDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getDailyCalorieIntake(),
                user.getDailyCalorieConsumption()
        );
    }

    public User mapToUser(UserDTO userDto) {
        return new User(
                userDto.getId(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getDailyCalorieIntake(),
                userDto.getDailyCalorieConsumption()
        );
    }

    public List<UserDTO> mapToUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}

