package com.calories.end.services;

import com.calories.end.domain.User;
import com.calories.end.dto.ReportDTO;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final UserRepository userRepository;

    public ReportDTO generateDailyReport(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        double dailyCalorieIntake = user.getDailyCalorieIntake();
        double dailyCalorieConsumption = user.getDailyCalorieConsumption();
        double calorieBalance = dailyCalorieIntake - dailyCalorieConsumption;

        List<String> recipeNames = user.getRecipes().stream()
                .map(recipe -> recipe.getName())
                .collect(Collectors.toList());

        return new ReportDTO(
                user.getUsername(),
                LocalDate.now(),
                dailyCalorieIntake,
                dailyCalorieConsumption,
                calorieBalance,
                recipeNames
        );
    }
}