package com.project.mestresala.mestresalabe.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.mestresala.mestresalabe.model.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity(name = "reservation")
public class Reservation {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @Column(name = "date")
  private Date date;
  @Column(name = "title")
  private String title;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  @Column(name = "begin")
  private LocalTime begin;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  @Column(name = "end")
  private LocalTime end;
  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "owner", nullable = false)
  @JsonIgnoreProperties({
      "password",
      "role",
      "enabled",
      "authorities",
      "accountNonExpired",
      "credentialsNonExpired",
      "accountNonLocked"})
  private User user;
  @ManyToOne @JoinColumn(name = "room", nullable = false)
  private Room room;

  public void updateReservationObject(Reservation otherReservation) {
    this.setDate(otherReservation.getDate());
    this.setTitle(otherReservation.getTitle());
    this.setBegin(otherReservation.getBegin());
    this.setEnd(otherReservation.getEnd());
    this.setDescription(otherReservation.getDescription());
    this.setUser(otherReservation.getUser());
    this.setRoom(otherReservation.getRoom());
  }
}
