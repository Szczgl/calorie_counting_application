package com.calories.end.services;

import com.calories.end.domain.User;
import com.calories.end.dto.UserDTO;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.UserMapper;
import com.calories.end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.mapToUserDto(user);
    }

    public UserDTO saveUser(UserDTO userDto) {
        User user = userMapper.mapToUser(userDto);
        return userMapper.mapToUserDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(UserDTO userDto, Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userDto.setId(id);
        User user = userMapper.mapToUser(userDto);
        return userMapper.mapToUserDto(userRepository.save(user));
    }
}

