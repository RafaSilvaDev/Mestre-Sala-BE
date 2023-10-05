package com.project.mestresala.mestresalabe.services;

import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.repository.RoomRepository;
import com.project.mestresala.mestresalabe.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class RoomService {
  @Autowired
  private RoomRepository roomRepository;

  public List<Room> getAllRooms() {
    return roomRepository.findAll();
  }

  public Room getRoomById(Long id) {
    Room room = roomRepository.findById(id).orElse(null);
    if (room == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Room not found."
      );
    else
      return room;
  }

  public ResponseEntity<Object> createRoomObject(Room roomToCreate) {
    if (roomToCreate.getTitle() == null || roomToCreate.getTitle().isEmpty()) {
      return ResponseHandler.generateResponse("Title must not be null to a Room object.",
          HttpStatus.BAD_REQUEST, null);
    }
    if (roomToCreate.getLocation() == null || roomToCreate.getLocation().isEmpty()) {
      return ResponseHandler.generateResponse("Location must not be null to a Room object.",
          HttpStatus.BAD_REQUEST, null);
    }
    if (roomToCreate.getDescription() == null || roomToCreate.getDescription().isEmpty()) {
      return ResponseHandler.generateResponse("Description must not be null to a Room object.",
          HttpStatus.BAD_REQUEST, null);
    }
    roomRepository.save(roomToCreate);
    return ResponseEntity.noContent().build();
  }
}
