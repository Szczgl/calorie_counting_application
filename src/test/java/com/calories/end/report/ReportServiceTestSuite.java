package com.calories.end.report;

import com.calories.end.domain.Recipe;
import com.calories.end.domain.User;
import com.calories.end.dto.ReportDTO;
import com.calories.end.exception.UserNotFoundException;
import com.calories.end.repository.UserRepository;
import com.calories.end.services.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTestSuite {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateDailyReport_UserFound_ReturnsReportDTO() throws UserNotFoundException {
        // GIVEN
        User user = new User();
        user.setId(1L);
        user.setUsername("Tom");
        user.setDailyCalorieIntake(2100);
        user.setDailyCalorieConsumption(430);

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test");

        user.setRecipes(Collections.singletonList(recipe));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // WHEN
        ReportDTO reportDTO = reportService.generateDailyReport(1L);

        // THEN
        assertNotNull(reportDTO);
        assertEquals("Tom", reportDTO.getUsername());
        assertEquals(2100, reportDTO.getDailyCalorieIntake());
        assertEquals(430, reportDTO.getDailyCalorieConsumption());
        assertEquals(1670, reportDTO.getCalorieBalance());
        assertEquals(1, reportDTO.getRecipesName().size());
        assertEquals("Test", reportDTO.getRecipesName().get(0));
    }

    @Test
    void generateDailyReport_UserNotFound_ThrowsUserNotFoundException() {
        // GIVEN
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> reportService.generateDailyReport(1L));
    }
}
