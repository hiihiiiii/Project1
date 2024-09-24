package com.utc.dormitory_managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utc.dormitory_managing.entity.Room;

public interface RoomRepo extends JpaRepository<Room, String>  {

}
