package com.utc.dormitory_managing.dto;

import com.utc.dormitory_managing.entity.Floor;
import com.utc.dormitory_managing.entity.RoomType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
	private String roomId;
	
	private String roomName;
	
	private String buildingDescription;
	
	private RoomType roomType;
	
	private Floor floor;
	
	private int maxNumber;
	
	private int roomNumber;
	
	private String roomStatus;
}
