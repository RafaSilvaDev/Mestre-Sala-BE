package com.project.mestresala.mestresalabe.repository;

import com.project.mestresala.mestresalabe.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
