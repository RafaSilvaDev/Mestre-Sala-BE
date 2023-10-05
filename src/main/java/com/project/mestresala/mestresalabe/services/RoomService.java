package com.project.mestresala.mestresalabe.services;

import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
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

  public void createRoom(Room roomToCreate) {
    if (roomToCreate.getTitle() == null || roomToCreate.getTitle().isEmpty())
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title must not be null to a Room object.");
    if (roomToCreate.getLocation() == null || roomToCreate.getLocation().isEmpty())
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Location must not be null to a Room object.");
    if (roomToCreate.getDescription() == null || roomToCreate.getDescription().isEmpty())
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description must not be null to a Room object.");

    roomRepository.save(roomToCreate);
  }

  public void deleteRoom(Long id) {
    Room room = roomRepository.findById(id).orElse(null);
    if (room == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Room not found."
      );
    else
      roomRepository.delete(room);
  }

  public void updateRoom(Long id, Room updatedRoom) {
    Room room = roomRepository.findById(id).orElse(null);
    if (room == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Room not found."
      );
    else {
      try {
        room.updateRoomObject(updatedRoom);
        roomRepository.save(room);
      } catch (Exception e) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "All fields must not be null to update a Room object."
        );
      }
    }
  }
}
