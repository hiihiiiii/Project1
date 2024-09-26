package com.utc.dormitory_managing.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "contract")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper= false)

public class Contract extends BaseModel{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name= "contract_id")
	private String contractId;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "staff_id")
	private Staff staff;
	
	@ManyToOne
	@JoinColumn(name ="room_id")
	private Room room;
	
	//service tu cac phong va service bo sung
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "service_roomType", joinColumns = @JoinColumn (name = "room_type_id"), inverseJoinColumns = @JoinColumn(name = "service_id"))
	private Set<Services> services;
	
	@Column
	//chi phí miễn giảm
	private Long reduceCost;
	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private String status;
}
