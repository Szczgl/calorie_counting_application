package com.calories.end.services;

import com.calories.end.domain.User;
import com.calories.end.dto.UserDTO;
import com.calories.end.mapper.UserMapper;
import com.calories.end.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow());
    }

    public UserDTO createUser(UserDTO userDTO) {
        return userMapper.toDto(userRepository.save(userMapper.toEntity(userDTO)));
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    User updatedUser = userMapper.toEntity(userDTO);
                    updatedUser.setId(existingUser.getId());
                    return userMapper.toDto(userRepository.save(updatedUser));
                })
                .orElseThrow();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

