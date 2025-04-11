package com.pozteam.pubgPoint.controller;

import com.pozteam.pubgPoint.controllers.IslandController;
import com.pozteam.pubgPoint.models.Island;
import com.pozteam.pubgPoint.services.IslandService;
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
import static org.mockito.Mockito.*;

public class IslandControllerTest {
    @Mock
    private IslandService islandService;

    @InjectMocks
    private IslandController islandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIslands() {
        // Подготовка данных
        Island island1 = new Island("Erangel");
        Island island2 = new Island("Miramar");
        when(islandService.getAllIslands()).thenReturn(Arrays.asList(island1, island2));

        // Вызов метода
        ResponseEntity<List<Island>> response = islandController.getAllIslands();

        // Проверка результата
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(islandService, times(1)).getAllIslands();
    }
}
