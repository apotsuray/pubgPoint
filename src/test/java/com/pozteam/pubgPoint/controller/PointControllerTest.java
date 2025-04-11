package com.pozteam.pubgPoint.controller;

import com.pozteam.pubgPoint.controllers.PointController;
import com.pozteam.pubgPoint.models.Point;
import com.pozteam.pubgPoint.services.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class PointControllerTest {
    @Mock
    private PointService pointService;

    @InjectMocks
    private PointController pointController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPoints() {
        // Подготовка данных
        Point point1 = new Point(null, 100, 200, 5, "Hilltop");
        Point point2 = new Point(null, 150, 250, 3, "Forest Edge");
        when(pointService.getAllPoints()).thenReturn(Arrays.asList(point1, point2));

        // Вызов метода
        ResponseEntity<List<Point>> response = pointController.getAllPoints();

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Hilltop", response.getBody().get(0).getDescription());
        verify(pointService, times(1)).getAllPoints();
    }

    @Test
    void testGetPointsByIslandId() {
        // Подготовка данных
        Point point = new Point(null, 100, 200, 5, "Hilltop");
        when(pointService.getPointsByIslandId(1)).thenReturn(Arrays.asList(point));

        // Вызов метода
        ResponseEntity<List<Point>> response = pointController.getPointsByIslandId(1);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Hilltop", response.getBody().get(0).getDescription());
        verify(pointService, times(1)).getPointsByIslandId(1);
    }

    @Test
    void testCreatePoint() {
        // Подготовка данных
        Point point = new Point(null, 100, 200, 5, "Hilltop");
        when(pointService.createPoint(point)).thenReturn(point);

        // Вызов метода
        ResponseEntity<Point> response = pointController.createPoint(point);

        // Проверка результата
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hilltop", response.getBody().getDescription());
        verify(pointService, times(1)).createPoint(point);
    }

    @Test
    void testUpdatePoint() {
        // Подготовка данных
        Point updatedPoint = new Point(null, 150, 250, 4, "Updated Hilltop");
        when(pointService.updatePoint(1, updatedPoint)).thenReturn(updatedPoint);

        // Вызов метода
        ResponseEntity<Point> response = pointController.updatePoint(1, updatedPoint);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Hilltop", response.getBody().getDescription());
        verify(pointService, times(1)).updatePoint(1, updatedPoint);
    }

    @Test
    void testDeletePoint() {
        // Подготовка данных
        when(pointService.deletePoint(1)).thenReturn("Точка удалена с ID: 1");

        // Вызов метода
        ResponseEntity<String> response = pointController.deletePoint(1);

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Точка удалена с ID: 1", response.getBody());
        verify(pointService, times(1)).deletePoint(1);
    }
}
