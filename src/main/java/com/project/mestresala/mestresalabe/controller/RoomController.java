package com.project.mestresala.mestresalabe.controller;

import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.services.RoomService;
import com.project.mestresala.mestresalabe.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  private RoomService roomService;

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
      @ApiResponse(responseCode = "404", description = Constants.ROOM_NOT_FOUND,
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
      @ApiResponse(responseCode = "400", description = "{attribute} must not be null to a Room object.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createRoom(@RequestBody Room room) {
    roomService.createRoom(room);
  }

  @Operation(summary = "Delete a room")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "403", description = "Access denied. Login required.",
          content = @Content),
      @ApiResponse(responseCode = "404", description = Constants.ROOM_NOT_FOUND,
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteRoom(@PathVariable(value = "id") Long id) {
    roomService.deleteRoom(id);
  }

  @Operation(summary = "Update a room")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "403", description = "Access denied. Login required.",
          content = @Content),
      @ApiResponse(responseCode = "404", description = Constants.ROOM_NOT_FOUND,
          content = @Content),
      @ApiResponse(responseCode = "400", description = "All fields must not be null to update a Room object.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateRoom(
      @PathVariable(value = "id") Long id,
      @RequestBody Room roomDetails) {
    roomService.updateRoom(id, roomDetails);
  }
}
