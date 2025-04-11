package com.pozteam.pubgPoint.service;

import com.pozteam.pubgPoint.models.Island;
import com.pozteam.pubgPoint.repositories.IslandRepository;
import com.pozteam.pubgPoint.services.IslandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class IslandServiceTest {
    @Mock
    private IslandRepository islandRepository;

    @InjectMocks
    private IslandService islandService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllIslands() {
        Island island1 = new Island("Erangel");
        Island island2 = new Island("Miramar");
        when(islandRepository.findAll()).thenReturn(Arrays.asList(island1, island2));

        List<Island> islands = islandService.getAllIslands();

        assertEquals(2, islands.size());
        assertEquals("Erangel", islands.get(0).getName());
        verify(islandRepository, times(1)).findAll();
    }
    @Test
    void testGetIslandById() {
        // Подготовка данных
        Island island = new Island("Erangel");
        island.setId(1);
        when(islandRepository.findById(1)).thenReturn(Optional.of(island));

        // Вызов метода
        Island result = islandService.getIslandById(1);

        // Проверка результата
        assertNotNull(result);
        assertEquals("Erangel", result.getName());
        verify(islandRepository, times(1)).findById(1);
    }

    @Test
    void testCreateIsland() {
        // Подготовка данных
        Island island = new Island("Erangel");
        when(islandRepository.save(island)).thenReturn(island);

        // Вызов метода
        Island result = islandService.createIsland(island);

        // Проверка результата
        assertNotNull(result);
        assertEquals("Erangel", result.getName());
        verify(islandRepository, times(1)).save(island);
    }
}
