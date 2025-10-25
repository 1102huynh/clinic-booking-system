package huynh.tdt.clinicbookingsystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class DashboardControllerTest {

    private DashboardController dashboardController = new DashboardController();

    @Test
    void testDashboardPage() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        String viewName = dashboardController.dashboard(model);

        // Assert
        assertEquals("dashboard", viewName);
        assertNotNull(viewName);
    }

    @Test
    void testDashboardPageReturnType() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        Object result = dashboardController.dashboard(model);

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof String);
    }

    @Test
    void testDashboardPageCorrectViewName() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        String viewName = dashboardController.dashboard(model);

        // Assert
        assertEquals("dashboard", viewName);
    }

    @Test
    void testDashboardPageMultipleCalls() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        String result1 = dashboardController.dashboard(model);
        String result2 = dashboardController.dashboard(model);

        // Assert
        assertEquals(result1, result2);
        assertEquals("dashboard", result1);
    }

    @Test
    void testDashboardPageNotNull() {
        // Arrange
        Model model = mock(Model.class);

        // Act
        String viewName = dashboardController.dashboard(model);

        // Assert
        assertNotNull(viewName);
        assertFalse(viewName.isEmpty());
    }
}
