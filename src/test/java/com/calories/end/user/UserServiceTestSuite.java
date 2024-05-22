package com.calories.end.user;

import com.calories.end.domain.User;
import com.calories.end.dto.UserDTO;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.mapper.UserMapper;
import com.calories.end.repository.UserRepository;
import com.calories.end.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTestSuite {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetAllUsers() {
        // GIVEN
        User user = new User();
        UserDTO userDTO = new UserDTO();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDTO);

        // WHEN
        List<UserDTO> result = userService.getAllUsers();

        // THEN
        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() throws UserNotFoundException {
        // GIVEN
        User user = new User();
        UserDTO userDTO = new UserDTO();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDTO);

        // WHEN
        UserDTO result = userService.getUserById(1L);

        // THEN
        assertNotNull(result);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveUser() {
        // GIVEN
        User user = new User();
        UserDTO userDTO = new UserDTO();
        when(userMapper.mapToUser(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDTO);

        // WHEN
        UserDTO result = userService.saveUser(userDTO);

        // THEN
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // WHEN
        userService.deleteUser(1L);

        // THEN
        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testUpdateUser() throws UserNotFoundException {
        // GIVEN
        User user = new User();
        UserDTO userDTO = new UserDTO();
        when(userRepository.existsById(anyLong())).thenReturn(true);
        when(userMapper.mapToUser(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.mapToUserDto(any(User.class))).thenReturn(userDTO);

        // WHEN
        UserDTO result = userService.updateUser(userDTO, 1L);

        // THEN
        assertNotNull(result);
        verify(userRepository, times(1)).existsById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        // GIVEN
        UserDTO userDTO = new UserDTO();
        when(userRepository.existsById(anyLong())).thenReturn(false);

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userDTO, 1L));
        verify(userRepository, times(1)).existsById(anyLong());
        verify(userRepository, never()).save(any(User.class));
    }
}
