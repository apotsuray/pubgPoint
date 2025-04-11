package com.pozteam.pubgPoint.service;

import com.pozteam.pubgPoint.models.Point;
import com.pozteam.pubgPoint.repositories.PointRepository;
import com.pozteam.pubgPoint.services.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PointServiceTest {
    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PointService pointService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPoints() {
        // Подготовка данных
        Point point1 = new Point(null, 100, 200, 5, "Hilltop");
        Point point2 = new Point(null, 150, 250, 3, "Forest Edge");
        when(pointRepository.findAll()).thenReturn(Arrays.asList(point1, point2));

        // Вызов метода
        List<Point> points = pointService.getAllPoints();

        // Проверка результата

        assertEquals(2, points.size());
        assertEquals("Hilltop", points.get(0).getDescription());
        verify(pointRepository, times(1)).findAll();
    }

    @Test
    void testGetPointsByIslandId() {
        // Подготовка данных
        Point point = new Point(null, 100, 200, 5, "Hilltop");
        when(pointRepository.findByIslandId(1)).thenReturn(Arrays.asList(point));

        // Вызов метода
        List<Point> points = pointService.getPointsByIslandId(1);

        // Проверка результата
        assertEquals(1, points.size());
        assertEquals("Hilltop", points.get(0).getDescription());
        verify(pointRepository, times(1)).findByIslandId(1);
    }
}
