package com.calories.end.scheduler;

import com.calories.end.domain.User;
import com.calories.end.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DailyCalorieResetScheduler {

    private final UserRepository userRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetDailyCalorieConsumption() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setDailyCalorieIntake(0);
            userRepository.save(user);
        }
    }
}