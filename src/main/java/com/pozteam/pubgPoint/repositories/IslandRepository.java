package com.pozteam.pubgPoint.repositories;

import com.pozteam.pubgPoint.models.Island;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IslandRepository extends JpaRepository<Island, Integer> {
}
