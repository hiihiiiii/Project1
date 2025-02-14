package com.utc.dormitory_managing.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utc.dormitory_managing.entity.Room;

public interface RoomRepo extends JpaRepository<Room, String>  {
	@Query("SELECT Count(a.roomId) from Room a where a.roomType.roomTypeId = :x ")
	Long getRoomNumber(@Param("x") String roomTypeId);
	
	@Query("SELECT a from Room a where a.roomType.roomTypeId = :x ")
	List<Room> findByRoomType(@Param("x") String roomTypeId);
}
