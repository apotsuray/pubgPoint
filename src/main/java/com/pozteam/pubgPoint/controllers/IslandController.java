package com.pozteam.pubgPoint.controllers;

import com.pozteam.pubgPoint.models.Island;
import com.pozteam.pubgPoint.services.IslandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/island")
public class IslandController {
    private final IslandService islandService;
    @Autowired
    public IslandController(IslandService islandService) {
        this.islandService = islandService;
    }
    @GetMapping()
    public ResponseEntity<List<Island>> getAllIslands() {
        List<Island> islands = islandService.getAllIslands();
        return new ResponseEntity<>(islands, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Island> getIslandById(@PathVariable("id") Integer id) {
        Island island = islandService.getIslandById(id);
        return new ResponseEntity<>(island,HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Island> createIsland(@RequestBody Island island) {
        Island createdIsland =  islandService.createIsland(island);
        return new ResponseEntity<>(createdIsland,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Island> updateIsland(@PathVariable("id") Integer id, @RequestBody Island islandDetails) {
        Island updatedIsland = islandService.updateIsland(id, islandDetails);
        return new ResponseEntity<>(updatedIsland, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIsland(@PathVariable("id") Integer id) {
        String message = islandService.deleteIsland(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
