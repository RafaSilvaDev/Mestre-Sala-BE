package com.project.mestresala.mestresalabe.controller;

import com.project.mestresala.mestresalabe.model.Reservation;
import com.project.mestresala.mestresalabe.model.Room;
import com.project.mestresala.mestresalabe.repository.ReservationRepository;
import com.project.mestresala.mestresalabe.response.ResponseHandler;
import com.project.mestresala.mestresalabe.services.ReservationService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@Tag(name = "Reservation")
@RequestMapping("/reservation")
public class ReservationController {
  @Autowired
  private ReservationService reservationService;

  @Operation(summary = "Get all reservations by date")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Reservation.class))})})
  @GetMapping("/{date}")
  @ResponseStatus(HttpStatus.OK)
  public List<Reservation> getReservations(@PathVariable(value = "date") String date) {
    return reservationService.getAllReservationsByDate(date);
  }

  /*@Operation(summary = "Get a reservation by it's id.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = Room.class))}),
      @ApiResponse(responseCode = "404", description = "Reservation not found.",
          content = @Content)})
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Reservation getReservationById(@PathVariable(value = "id") Long id) {
    return reservationService.getReservationById(id);
  }*/

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
  @ResponseStatus(HttpStatus.CREATED)
  public void createReservation(@RequestBody Reservation reservation) {
    reservationService.createReservation(reservation);
  }

  @Operation(summary = "Delete a reservation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "404", description = "Reservation not found.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReservation(@PathVariable(value = "id") Long id) {
    reservationService.deleteReservationById(id);
  }

  @Operation(summary = "Update a reservation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204"),
      @ApiResponse(responseCode = "404", description = "Reservation not found.",
          content = @Content),
      @ApiResponse(responseCode = "400", description = "All fields must not be null to update a Reservation object.",
          content = @Content)})
  @SecurityRequirement(name = "bearerAuth")
  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void updateReservation(
      @PathVariable(value = "id") Long id,
      @RequestBody Reservation reservationDetails) {
    reservationService.updateReservation(id, reservationDetails);
  }
}
