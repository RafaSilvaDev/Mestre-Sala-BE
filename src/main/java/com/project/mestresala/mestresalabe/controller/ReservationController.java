package com.project.mestresala.mestresalabe.controller;

import com.project.mestresala.mestresalabe.model.Reservation;
import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.repository.ReservationRepository;
import com.project.mestresala.mestresalabe.response.ResponseHandler;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@RestController
@Tag(name = "Reservation")
@RequestMapping("/reservation")
public class ReservationController {
  @Autowired
  private ReservationRepository reservationRepository;

  @Operation(summary = "Get all reservations")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Room.class))})})
  @GetMapping
  public ResponseEntity<Object> getReservations() {
    return ResponseEntity.ok(reservationRepository.findAll());
  }

  @Operation(summary = "Get a reservation by it's id.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Room.class))}),
      @ApiResponse(responseCode = "404", description = "Reservation not found.",
          content = @Content)})
  @GetMapping("/{id}")
  public ResponseEntity<Object> getReservationById(@PathVariable(value = "id") Long id) {
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if(reservation == null) {
      return ResponseHandler.generateResponse("Reservation not found.",
          HttpStatus.NOT_FOUND, null);
    } else {
      return ResponseEntity.ok(reservation);
    }
  }

  @Operation(summary = "Create a reservations")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201"),
      @ApiResponse(responseCode = "403", description = "Access denied. Login required.",
          content = @Content),
      @ApiResponse(responseCode = "409", description = "Time inserted overlaps an existing reservation.",
          content = @Content),
      @ApiResponse(responseCode = "409", description = "Field must not be null.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @PostMapping
  public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
    List<Reservation> existingReservations = reservationRepository
        .findByRoomAndDate(reservation.getRoom(), reservation.getDate());
    boolean overlap = existingReservations.stream()
        .anyMatch(existing -> doReservationsOverlap(existing, reservation));
    if (overlap) {
      return ResponseHandler.generateResponse(
          "Time inserted overlaps an existing reservation.",
          HttpStatus.BAD_REQUEST, null);
    } else {
      Reservation savedReservation = reservationRepository.save(reservation);
      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(savedReservation.getId())
          .toUri();
      return ResponseEntity.created(location).build();
    }
  }

  private boolean doReservationsOverlap(Reservation existing, Reservation newReservation) {
    LocalTime existingBegin = existing.getBegin();
    LocalTime existingEnd = existing.getEnd();
    LocalTime newBegin = newReservation.getBegin();
    LocalTime newEnd = newReservation.getEnd();

    return !(existingEnd.isBefore(newBegin) || existingBegin.isAfter(newEnd) ||
            existingEnd.equals(newBegin) || existingBegin.equals(newEnd));
  }

  @Operation(summary = "Delete a reservation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "404", description = "Reservation not found.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteReservation(@PathVariable(value = "id") Long id){
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if(reservation == null){
      return ResponseHandler.generateResponse(
          "Reservation not found.",
          HttpStatus.NOT_FOUND, null);
    }else{
      reservationRepository.delete(reservation);
      return ResponseEntity.noContent().build();
    }
  }

  @Operation(summary = "Update a reservation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "404", description = "Reservation not found.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @PutMapping("/{id}")
  public ResponseEntity<Object> updateReservation(
      @PathVariable(value = "id") Long id,
      @RequestBody Reservation reservationDetails){
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if(reservation == null){
      return ResponseHandler.generateResponse(
          "Reservation not found.",
          HttpStatus.NOT_FOUND, null);
    }else{
      reservation.updateReservationObject(reservationDetails);
      reservationRepository.save(reservation);
      return ResponseEntity.noContent().build();
    }
  }
}
