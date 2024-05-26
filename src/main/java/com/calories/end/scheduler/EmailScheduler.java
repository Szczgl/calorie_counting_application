package com.calories.end.scheduler;
import com.calories.end.dto.UserDTO;
import com.calories.end.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    @Scheduled(cron = "0 30 14 * * ?")
    public void sendDailyCalorieReport() {
        for (UserDTO userDTO : userService.getAllUsers()) {
            double calorieIntake = userDTO.getDailyCalorieIntake();
            double calorieConsumption = userDTO.getDailyCalorieConsumption();
            double calorieDeficit = calorieIntake - calorieConsumption;

            String message = String.format("Hi %s, your daily calorie deficit/intake is: %.2f", userDTO.getUsername(), calorieDeficit);

            sendEmail(userDTO.getEmail(), "Daily Calorie Report", message);
        }
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
