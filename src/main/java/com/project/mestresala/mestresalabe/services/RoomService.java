package com.project.mestresala.mestresalabe.services;

import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.repository.RoomRepository;
import com.project.mestresala.mestresalabe.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
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
          Constants.ROOM_NOT_FOUND
      );
    else
      return room;
  }

  public void createRoom(Room roomToCreate) {
    roomRepository.save(roomToCreate);
  }

  public void deleteRoom(Long id) {
    Room room = roomRepository.findById(id)
        .orElse(null);
    if (room == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          Constants.ROOM_NOT_FOUND
      );
    else
      roomRepository.delete(room);
  }

  public void updateRoom(Long id, Room updatedRoom) {
    Room room = roomRepository.findById(id).orElse(null);
    if (room == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          Constants.ROOM_NOT_FOUND
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
