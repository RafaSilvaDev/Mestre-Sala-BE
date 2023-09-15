package com.project.mestresala.mestresalabe.controller;


import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.repository.RoomRepository;
import com.project.mestresala.mestresalabe.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoomById(@PathVariable(value = "id") Long id){
        Room room = roomRepository.findById(id).orElse(null);
        if(room == null){
            return ResponseHandler.generateResponse("Object not found.",
                    HttpStatus.NOT_FOUND, null);
        }else{
            return ResponseHandler.generateResponse(
                "",
                HttpStatus.OK, room);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room createRoom(@RequestBody Room room){
        return roomRepository.save(room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable(value = "id") Long id){
        Room room = roomRepository.findById(id).orElse(null);
        if(room == null){
            return ResponseHandler.generateResponse(
                    "Object not found.",
                    HttpStatus.NOT_FOUND, null);
        }else{
            roomRepository.delete(room);
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRoom(
            @PathVariable(value = "id") Long id,
            @RequestBody Room roomDetails){
        Room room = roomRepository.findById(id).orElse(null);
        if(room == null){
            return ResponseHandler.generateResponse(
                    "Object not found.",
                    HttpStatus.NOT_FOUND, null);
        }else{
            room.updateRoomObject(roomDetails);
            roomRepository.save(room);
            return ResponseEntity.noContent().build();
        }
    }
}
