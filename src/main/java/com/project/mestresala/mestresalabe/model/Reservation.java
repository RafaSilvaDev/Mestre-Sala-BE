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
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.sql.Time;
import java.time.Instant;
import java.util.Date;

@Data
@Entity(name = "reservation")
public class Reservation {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "date")
  private Date date;
  @Column(name = "title")
  private String title;
  @Column(name = "begin")
  private Time begin;
  @Column(name = "end")
  private Time end;
  @Column(name = "description")
  private String description;

  @OneToOne @JoinColumn(name = "owner", nullable = false)
  @JsonIgnoreProperties({"password", "role", "enabled", "authorities", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
  private User user;
  @OneToOne @JoinColumn(name = "room", nullable = false)
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
