package com.utc.dormitory_managing.dto;

import java.util.Date;

import lombok.Data;

@Data
public class StudentDTO {

	private String studentId;
	
	private String fullname;
	
	private Date dateOfBirth;
	
	private String studentAddress;
	
	private Boolean studentGender;
	
	private String studentIdentification;
	
	private String phoneNumber;
	
	private String relativesPhone;
	
	private String studentEmail;
	
	private int studentPriority;
}
