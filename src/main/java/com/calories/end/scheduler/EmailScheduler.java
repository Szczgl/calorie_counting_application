package com.calories.end.scheduler;
import com.calories.end.dto.UserDTO;
import com.calories.end.services.EmailService;
import com.calories.end.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final EmailService emailService;

    @Autowired
    public EmailScheduler(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.emailService = EmailService.getInstance(javaMailSender);
    }

    @Scheduled(cron = "0 0 22 * * ?")
    public void sendDailyCalorieReport() {
        for (UserDTO userDTO : userService.getAllUsers()) {
            double calorieIntake = userDTO.getDailyCalorieIntake();
            double calorieConsumption = userDTO.getDailyCalorieConsumption();
            double calorieDeficit = calorieIntake - calorieConsumption;

            String message = String.format("Hi %s, your daily calorie deficit/intake is: %.2f", userDTO.getUsername(), calorieDeficit);
            emailService.sendCalorieReport(userDTO.getEmail(), "Daily Calorie Report", message);
        }
    }
}
