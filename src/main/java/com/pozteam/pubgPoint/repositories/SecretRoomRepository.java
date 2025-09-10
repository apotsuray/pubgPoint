package com.pozteam.pubgPoint.repositories;

import com.pozteam.pubgPoint.models.SecretRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecretRoomRepository extends JpaRepository<SecretRoom, Integer> {
    List<SecretRoom> findByIslandId(Integer islandId);
}
