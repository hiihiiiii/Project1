package com.utc.dormitory_managing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "room")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper= false)

public class Room extends BaseModel{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name ="room_id")
	private String roomId;
	
	@Column(name= "room_name")
	private String roomName;
	
	@Column(name= "room_des")
	private String buildingDescription;
	
	@ManyToOne
	@JoinColumn(name = "room_type_id")
	private RoomType roomType;
	
	@ManyToOne
	@JoinColumn(name = "floor_id")
	private Floor floor;
	
	@Column
	private int maxNumber;
	
	@Column
	private int roomNumber;
	
	@Column
	private String roomStatus;
	
}
