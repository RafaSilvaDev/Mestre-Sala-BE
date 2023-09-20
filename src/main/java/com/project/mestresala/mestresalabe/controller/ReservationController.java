package com.project.mestresala.mestresalabe.controller;

import com.project.mestresala.mestresalabe.model.Reservation;
import com.project.mestresala.mestresalabe.repository.ReservationRepository;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
  @Autowired
  private ReservationRepository reservationRepository;

  @GetMapping
  public ResponseEntity<Object> getReservations() {
    return ResponseEntity.ok(reservationRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getReservationById(@PathVariable(value = "id") Long id) {
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if(reservation == null) {
      return ResponseHandler.generateResponse("Object not found.",
          HttpStatus.NOT_FOUND, null);
    } else {
      return ResponseEntity.ok(reservation);
    }
  }

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

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> deleteReservation(@PathVariable(value = "id") Long id){
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if(reservation == null){
      return ResponseHandler.generateResponse(
          "Object not found.",
          HttpStatus.NOT_FOUND, null);
    }else{
      reservationRepository.delete(reservation);
      return ResponseEntity.noContent().build();
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateReservation(
      @PathVariable(value = "id") Long id,
      @RequestBody Reservation reservationDetails){
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if(reservation == null){
      return ResponseHandler.generateResponse(
          "Object not found.",
          HttpStatus.NOT_FOUND, null);
    }else{
      reservation.updateReservationObject(reservationDetails);
      reservationRepository.save(reservation);
      return ResponseEntity.noContent().build();
    }
  }
}
