package com.pozteam.pubgPoint.services;

import com.pozteam.pubgPoint.models.Island;
import com.pozteam.pubgPoint.repositories.IslandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class IslandService {
    private final IslandRepository islandRepository;
    @Autowired
    public IslandService(IslandRepository islandRepository) {
        this.islandRepository = islandRepository;
    }
    public List<Island> getAllIslands() {
        return islandRepository.findAll();
    }
    public Island getIslandById(Integer id) {
        return islandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена с ID: " + id));
    }
    @Transactional
    public Island createIsland(Island island) {
        return islandRepository.save(island);
    }
    @Transactional
    public Island updateIsland(Integer id, Island islandDetails) {
        Island island = islandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена с ID: " + id));
        island.setName(islandDetails.getName());
        return islandRepository.save(island);
    }
    @Transactional
    public String deleteIsland(Integer id) {
        Island island = islandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Карта не найдена с ID: " + id));
        islandRepository.delete(island);
        return "Карта удалена с ID: " + id;
    }
}
