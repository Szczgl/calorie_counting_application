package com.calories.end.controller;

import com.calories.end.domain.Activity;
import com.calories.end.domain.Recipe;
import com.calories.end.dto.ReportDTO;
import com.calories.end.dto.UserDTO;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.services.ReportService;
import com.calories.end.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDto, @PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(userService.updateUser(userDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/recipes")
    public ResponseEntity<Void> addRecipeToUser(@PathVariable Long userId, @RequestBody Recipe recipe) throws UserNotFoundException {
        userService.addRecipeToUser(userId, recipe);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/activities")
    public ResponseEntity<Void> addActivityToUser(@PathVariable Long userId, @RequestBody Activity activity) throws UserNotFoundException {
        userService.addActivityToUser(userId, activity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/daily-report")
    public ResponseEntity<ReportDTO> getDailyReport(@PathVariable Long id) throws UserNotFoundException {
        ReportDTO report = reportService.generateDailyReport(id);
        return ResponseEntity.ok(report);
    }
}


