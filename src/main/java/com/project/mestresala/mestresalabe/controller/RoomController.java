package com.project.mestresala.mestresalabe.controller;

import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.repository.RoomRepository;
import com.project.mestresala.mestresalabe.response.ResponseHandler;
import com.project.mestresala.mestresalabe.services.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Room")
@RequestMapping("/room")
public class RoomController {

  @Autowired private RoomService roomService;

  @Autowired
  private RoomRepository roomRepository;

  @Operation(summary = "Get all rooms available")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Room.class))})})
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Room> getRooms() {
    return roomService.getAllRooms();
  }

  @Operation(summary = "Get a room by it's id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Room.class))}),
      @ApiResponse(responseCode = "404", description = "Room not found",
          content = @Content)})
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Room getRoomById(@PathVariable(value = "id") Long id) {
    return roomService.getRoomById(id);
  }

  @Operation(summary = "Create a new room")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201"),
      @ApiResponse(responseCode = "403", description = "Access denied. Login required.",
          content = @Content),
      @ApiResponse(responseCode = "409", description = "Field must not be null.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Room createRoom(@RequestBody Room room) {
//        createRoomObject(room);
    return roomRepository.save(room);
  }

  @Operation(summary = "Delete a room")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "403", description = "Access denied. Login required.",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Room not found.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteRoom(@PathVariable(value = "id") Long id) {
    Room room = roomRepository.findById(id).orElse(null);
    if (room == null) {
      return ResponseHandler.generateResponse(
          "Room not found.",
          HttpStatus.NOT_FOUND, null);
    } else {

      roomRepository.delete(room);
      return ResponseEntity.noContent().build();
    }
  }

  @Operation(summary = "Update a room")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "403", description = "Access denied. Login required.",
          content = @Content),
      @ApiResponse(responseCode = "404", description = "Room not found.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateRoom(
      @PathVariable(value = "id") Long id,
      @RequestBody Room roomDetails) {
    Room room = roomRepository.findById(id).orElse(null);
    if (room == null) {
      return ResponseHandler.generateResponse(
          "Room not found.",
          HttpStatus.NOT_FOUND, null);
    } else {
//            createRoomObject(roomDetails);
      room.updateRoomObject(roomDetails);
      roomRepository.save(room);
      return ResponseEntity.noContent().build();
    }
  }
}
