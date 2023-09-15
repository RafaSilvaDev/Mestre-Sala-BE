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
            return ResponseHandler.generateResponse("O objeto não foi encontrado.",
                    HttpStatus.NOT_FOUND, null);
        }else{
            return ResponseHandler.generateResponse("O objeto foi encontrado",
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
                    "O objeto a ser excluído não foi encontrado.",
                    HttpStatus.NOT_FOUND, null);
        }else{
            roomRepository.delete(room);
            return ResponseHandler.generateResponse(
                    "O objeto foi excluído com sucesso",
                    HttpStatus.OK, room);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRoom(
            @PathVariable(value = "id") Long id,
            @RequestBody Room roomDetails){
        Room room = roomRepository.findById(id).orElse(null);
        if(room == null){
            return ResponseHandler.generateResponse(
                    "O objeto a ser atualizado não foi encontrado.",
                    HttpStatus.NOT_FOUND, null);
        }else{
            room.updateRoomObject(roomDetails);
            Room updatedPowerType = roomRepository.save(room);
            return ResponseHandler.generateResponse(
                    "O objeto foi atualizado com sucesso.",
                    HttpStatus.OK, updatedPowerType);
        }


    }
}
