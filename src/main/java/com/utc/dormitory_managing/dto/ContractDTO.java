package com.utc.dormitory_managing.dto;

import java.util.Date;

import com.utc.dormitory_managing.entity.Room;
//import com.utc.dormitory_managing.entity.Services;
import com.utc.dormitory_managing.entity.Staff;
import com.utc.dormitory_managing.entity.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractDTO {
	private String contractId;
	
	private Student student;
	
	private Staff staff;
	
	private Room room;
	
	private Long reduceCost;
	
	private Date startDate;
	
	private Date endDate;
}
