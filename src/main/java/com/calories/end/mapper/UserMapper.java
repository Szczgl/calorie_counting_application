package com.calories.end.mapper;

import com.calories.end.domain.User;
import com.calories.end.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        return new UserDTO();
    }

    public User toEntity(UserDTO userDTO) {
        return new User();
    }
}

