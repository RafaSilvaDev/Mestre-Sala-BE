package com.project.mestresala.mestresalabe.repository;

import com.project.mestresala.mestresalabe.model.Reservation;
import com.project.mestresala.mestresalabe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
  @Query("SELECT r FROM reservation r WHERE r.room = :room AND r.date = :date")
  List<Reservation> findByRoomAndDate(Room room, Date date);
  @Query("SELECT r FROM reservation r WHERE DATE(r.date) = :date ")
  List<Reservation> findByDate(Date date);
}
