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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
  @Autowired
  private ReservationRepository reservationRepository;

  @GetMapping
  public List<Reservation> getReservations() {
    return reservationRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getReservationById(@PathVariable(value = "id") Long id) {
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if(reservation == null) {
      return ResponseHandler.generateResponse("Object not found.",
          HttpStatus.NOT_FOUND, null);
    } else {
      return ResponseEntity.ok().build();
    }
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Reservation createReservation(@RequestBody Reservation reservation){
    return reservationRepository.save(reservation);
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
