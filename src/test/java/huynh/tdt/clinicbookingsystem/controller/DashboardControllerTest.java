package huynh.tdt.clinicbookingsystem.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardControllerTest {

    private DashboardController dashboardController = new DashboardController();

    @Test
    void testDashboardPage() {
        // Act
        String viewName = dashboardController.dashboard();

        // Assert
        assertEquals("dashboard", viewName);
        assertNotNull(viewName);
    }

    @Test
    void testDashboardPageReturnType() {
        // Act
        Object result = dashboardController.dashboard();

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof String);
    }

    @Test
    void testDashboardPageCorrectViewName() {
        // Act
        String viewName = dashboardController.dashboard();

        // Assert
        assertEquals("dashboard", viewName);
    }

    @Test
    void testDashboardPageMultipleCalls() {
        // Act
        String result1 = dashboardController.dashboard();
        String result2 = dashboardController.dashboard();

        // Assert
        assertEquals(result1, result2);
        assertEquals("dashboard", result1);
    }

    @Test
    void testDashboardPageNotNull() {
        // Act
        String viewName = dashboardController.dashboard();

        // Assert
        assertNotNull(viewName);
        assertFalse(viewName.isEmpty());
    }
}
