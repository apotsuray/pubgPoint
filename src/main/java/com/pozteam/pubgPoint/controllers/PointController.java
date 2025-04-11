package com.pozteam.pubgPoint.controllers;

import com.pozteam.pubgPoint.models.Point;
import com.pozteam.pubgPoint.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/point")
public class PointController {
    private final PointService pointService;
    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }
    @GetMapping
    public ResponseEntity<List<Point>> getAllPoints() {
        List<Point> points = pointService.getAllPoints();
        return new ResponseEntity<>(points, HttpStatus.OK);
    }
    @GetMapping("/island/{islandId}")
    public ResponseEntity<List<Point>> getPointsByIslandId(@PathVariable("islandId") Integer islandId) {
        List<Point> points = pointService.getPointsByIslandId(islandId);
        return new ResponseEntity<>(points, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<Point> createPoint(@RequestBody Point point) {
        Point createdPoint = pointService.createPoint(point);
        return new ResponseEntity<>(createdPoint, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Point> updatePoint(@PathVariable("id") Integer id, @RequestBody Point pointDetails) {
        Point updatedPoint = pointService.updatePoint(id, pointDetails);
        return new ResponseEntity<>(updatedPoint, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePoint(@PathVariable("id") Integer id) {
        String message = pointService.deletePoint(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
