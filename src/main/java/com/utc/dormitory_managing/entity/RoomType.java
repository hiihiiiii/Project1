package com.utc.dormitory_managing.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	
	//tinh tien theo dau nguoi
	@Column(name= "room_type_price")
	private Long roomTypePrice;
	
	@Column(name= "room_type_number")
	//suc chua toi da
	private int roomTypeNumber;
	
	
	
}
