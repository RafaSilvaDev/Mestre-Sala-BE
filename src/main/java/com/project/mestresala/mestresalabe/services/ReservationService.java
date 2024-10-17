package com.project.mestresala.mestresalabe.services;

import com.project.mestresala.mestresalabe.model.Reservation;
import com.project.mestresala.mestresalabe.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

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

  public List<Reservation> getAllReservationsByDate(String date) {
    try {
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
      Date dateToFilter = format.parse(date);

      List<Reservation> reservations = reservationRepository.findByDate(dateToFilter);
      if (reservations == null)
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Reservation not found."
        );
      else
        return reservations;
    } catch (Exception exception) {
      throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              "Could not execute request." + exception
      );
    }
  }


  public List<Reservation> getReservationByUserId(Long id) {
    try {
      List<Reservation> reservations = reservationRepository.findByUserId(id);
      if (reservations == null)
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found."
        );
      else
        return reservations.stream()
            .peek(reservation -> {
              LocalDateTime ldt = LocalDateTime.ofInstant(reservation.getDate().toInstant(),
                      ZoneId.of("America/Sao_Paulo"));
              ldt = ldt.minusDays(1);
              Date out = Date.from(ldt.atZone(ZoneId.of("America/Sao_Paulo")).toInstant());
              reservation.setDate(out);
            }).toList();
    } catch (Exception exception) {
      throw new ResponseStatusException(
              HttpStatus.BAD_REQUEST,
              "Could not execute request. " + exception
      );
    }
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

  public void updateReservation(Long id, Reservation updatedReservation) {
    Reservation reservation = reservationRepository.findById(id).orElse(null);
    if (reservation == null)
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          "Reservation not found."
      );
    else {
      try {
        reservation.updateReservationObject(updatedReservation);

        List<Reservation> existingReservations = reservationRepository
                .findByRoomAndDate(updatedReservation.getRoom(), updatedReservation.getDate());
        existingReservations.removeIf(r -> r.getId().equals(updatedReservation.getId()));
        boolean overlap = existingReservations.stream()
                .anyMatch(existing -> doReservationsOverlap(existing, updatedReservation));
        if (overlap) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Time inserted overlaps an existing reservation."
        );
        else
          reservationRepository.save(reservation);
      } catch (Exception e) {
        throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Error: " + e
        );
      }
    }
  }
}
