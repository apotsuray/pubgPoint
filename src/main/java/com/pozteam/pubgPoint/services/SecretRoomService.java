package com.pozteam.pubgPoint.services;

import com.pozteam.pubgPoint.models.SecretRoom;
import com.pozteam.pubgPoint.repositories.SecretRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class SecretRoomService {
    private final SecretRoomRepository secretRoomRepository;
    @Autowired
    public SecretRoomService(SecretRoomRepository secretRoomRepository) {
        this.secretRoomRepository = secretRoomRepository;
    }
    public List<SecretRoom> getAllSecretRooms() {
        return secretRoomRepository.findAll();
    }
    public List<SecretRoom> getSecretRoomsByIslandId(Integer islandId) {
        return secretRoomRepository.findByIslandId(islandId);
    }
    @Transactional
    public SecretRoom createSecretRoom(SecretRoom secretRoom) {
        return secretRoomRepository.save(secretRoom);
    }
    @Transactional
    public SecretRoom updateSecretRoom(Integer id, SecretRoom secretRoomDetails) {
        SecretRoom secretRoom = secretRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Секретная комната не найдена с ID: " + id));
        secretRoom.setxCoord(secretRoomDetails.getxCoord());
        secretRoom.setyCoord(secretRoomDetails.getyCoord());
        return secretRoomRepository.save(secretRoom);
    }
    @Transactional
    public String deleteSecretRoom(Integer id) {
        SecretRoom secretRoom = secretRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Секретная комната не найдена с ID: " + id));
        secretRoomRepository.delete(secretRoom);
        return "Секретная комната удалена с ID: " + id;
    }
}
