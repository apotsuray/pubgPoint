package com.pozteam.pubgPoint.controllers;

import com.pozteam.pubgPoint.models.SecretRoom;
import com.pozteam.pubgPoint.services.SecretRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/secret-room")
public class SecretRoomController {
    private final SecretRoomService secretRoomService;

    @Autowired
    public SecretRoomController(SecretRoomService secretRoomService) {
        this.secretRoomService = secretRoomService;
    }
    @GetMapping
    public ResponseEntity<List<SecretRoom>> getAllSecretRooms() {
        List<SecretRoom> secretRooms = secretRoomService.getAllSecretRooms();
        return new ResponseEntity<>(secretRooms, HttpStatus.OK);
    }

    @GetMapping("/island/{islandId}")
    public ResponseEntity<List<SecretRoom>> getSecretRoomsByIslandId(@PathVariable("islandId") Integer islandId) {
        List<SecretRoom> secretRooms = secretRoomService.getSecretRoomsByIslandId(islandId);
        return new ResponseEntity<>(secretRooms, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SecretRoom> createSecretRoom(@RequestBody SecretRoom secretRoom) {
        SecretRoom createdSecretRoom = secretRoomService.createSecretRoom(secretRoom);
        return new ResponseEntity<>(createdSecretRoom, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecretRoom> updateSecretRoom(@PathVariable("id") Integer id, @RequestBody SecretRoom secretRoomDetails) {
        SecretRoom updatedSecretRoom = secretRoomService.updateSecretRoom(id, secretRoomDetails);
        return new ResponseEntity<>(updatedSecretRoom, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSecretRoom(@PathVariable("id") Integer id) {
        String message = secretRoomService.deleteSecretRoom(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
