package com.project.mestresala.mestresalabe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Room {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "location")
    private String location;
    @Column(name = "description")
    private String description;

    public void updateRoomObject(Room otherRoom) {
        this.setTitle(otherRoom.getTitle());
        this.setLocation(otherRoom.getLocation());
        this.setDescription(otherRoom.getDescription());
    }
}
