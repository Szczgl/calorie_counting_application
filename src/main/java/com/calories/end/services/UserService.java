package com.calories.end.services;

import com.calories.end.domain.Activity;
import com.calories.end.domain.Recipe;
import com.calories.end.domain.User;
import com.calories.end.domain.observer.CalorieObserver;
import com.calories.end.dto.UserDTO;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.UserMapper;
import com.calories.end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private void initializeObservers(User user) {
        user.addObserver(new CalorieObserver());
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        initializeObservers(user);
        return userMapper.mapToUserDto(user);
    }

    public UserDTO saveUser(UserDTO userDto) {
        User user = userMapper.mapToUser(userDto);
        initializeObservers(user);
        return userMapper.mapToUserDto(userRepository.save(user));
    }

    public void deleteUser(Long id)  {
        userRepository.deleteById(id);
    }

    public UserDTO updateUser(UserDTO userDto, Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userDto.setId(id);
        User user = userMapper.mapToUser(userDto);
        initializeObservers(user);
        return userMapper.mapToUserDto(userRepository.save(user));
    }

    public void addRecipeToUser(Long userId, Recipe recipe) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        initializeObservers(user);
        user.addRecipe(recipe);
        userRepository.save(user);
    }

    public void addActivityToUser(Long userId, Activity activity) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        initializeObservers(user);
        user.addActivity(activity);
        userRepository.save(user);
    }
}

