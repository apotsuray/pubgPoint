package com.pozteam.pubgPoint.services;

import com.pozteam.pubgPoint.models.Point;
import com.pozteam.pubgPoint.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PointService {
    private final PointRepository pointRepository;
    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }
    public List<Point> getAllPoints() {
        return pointRepository.findAll();
    }
    public List<Point> getPointsByIslandId(Integer mapId) {
        return pointRepository.findByIslandId(mapId);
    }
    @Transactional
    public Point createPoint(Point point) {
        return pointRepository.save(point);
    }
    @Transactional
    public Point updatePoint(Integer id, Point pointDetails) {
        Point point = pointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Точка не найдена с ID: " + id));
        point.setxCoord(pointDetails.getxCoord());
        point.setyCoord(pointDetails.getyCoord());
        point.setBaseRating(pointDetails.getBaseRating());
        point.setDescription(pointDetails.getDescription());
        return pointRepository.save(point);
    }
    @Transactional
    public String deletePoint(Integer id) {
        Point point = pointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Точка не найдена с ID: " + id));
        pointRepository.delete(point);
        return "Точка удалена с ID: " + id;
    }
}
