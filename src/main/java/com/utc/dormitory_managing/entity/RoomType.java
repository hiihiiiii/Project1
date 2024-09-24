package com.utc.dormitory_managing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "room_type")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper= false)

public class RoomType extends BaseModel{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name ="room_type_id")
	private String roomTypeId;
	
	@Column(name= "room_type_name")
	private String roomTypeName;
	
	@Column(name= "room_type_des")
	private String roomTypeDes;
	
	@Column(name= "room_type_price")
	private Long roomTypePrice;
	
	
}
