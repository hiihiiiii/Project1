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
@Table(name = "requirement")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper= false)
public class Requirement extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name ="requirement_id")
	private String requirementId;
	
	@Column(name= "requirement_name")
	private String requirementName;
	
	@Column(name= "requirement_des")
	private String requirementDes;
	
	
	
	
}

