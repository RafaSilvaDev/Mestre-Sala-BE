package com.project.mestresala.mestresalabe.services;

import com.project.mestresala.mestresalabe.model.Reservation;
import com.project.mestresala.mestresalabe.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

  @Autowired
  private ReservationRepository reservationRepository;

  private boolean doReservationsOverlap(Reservation existing, Reservation newReservation) {
    LocalTime existingBegin = existing.getBegin();
    LocalTime existingEnd = existing.getEnd();
    LocalTime newBegin = newReservation.getBegin();
    LocalTime newEnd = newReservation.getEnd();

    return !(existingEnd.isBefore(newBegin) || existingBegin.isAfter(newEnd) ||
        existingEnd.equals(newBegin) || existingBegin.equals(newEnd));
  }

  public List<Reservation> getAllReservations() {
    return reservationRepository.findAll();
  }

  public Reservation getReservationById(Long id) {
    Reservation reservation = reservationRepository
        .findById(id)
        .orElse(null);
    if (reservation == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Reservation not found."
      );
    else
      return reservation;
  }

  public void createReservation(Reservation reservationToCreate) {
    List<Reservation> existingReservations = reservationRepository
        .findByRoomAndDate(reservationToCreate.getRoom(), reservationToCreate.getDate());
    boolean overlap = existingReservations.stream()
        .anyMatch(existing -> doReservationsOverlap(existing, reservationToCreate));

    if (overlap) throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Time inserted overlaps an existing reservation."
    );
    else reservationRepository.save(reservationToCreate);
  }

  public void deleteReservationById(Long id) {
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if (reservation == null)
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Reservation not found."
      );
    else reservationRepository.delete(reservation);
  }

  public void updateRoom(Long id, Reservation updatedReservation) {
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if (reservation == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Reservation not found."
      );
    else {
      try {
        reservation.updateReservationObject(updatedReservation);
        reservationRepository.save(reservation);
      } catch (Exception e) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "All fields must not be null to update a Reservation object."
        );
      }
    }
  }
}
