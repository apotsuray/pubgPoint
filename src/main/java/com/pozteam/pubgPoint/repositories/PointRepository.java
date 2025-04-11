package com.pozteam.pubgPoint.repositories;

import com.pozteam.pubgPoint.models.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Integer> {
    List<Point> findByIslandId(Integer islandId);
}
