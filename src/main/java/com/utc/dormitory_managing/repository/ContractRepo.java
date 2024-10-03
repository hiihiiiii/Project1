package com.utc.dormitory_managing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.utc.dormitory_managing.entity.Contract;

public interface ContractRepo extends JpaRepository<Contract, String>  {
	@Query("SELECT Count(a.contractId) from Contract a where a.roomType.roomTypeId = :x and a.contractStatus != :y")
	Long getContractNumber(@Param("x") String roomTypeId,@Param("y") String contractStatus );
	
	@Query("SELECT a from Contract a where a.contractStatus = :y")
	List<Contract> findContractByStatus(@Param("y") String contractStatus);
	
	@Query("SELECT a from Contract a where a.roomType.roomTypeId = :x and a.contractStatus = :y")
	List<Contract> findContractByStatusAndRoomType(@Param("x") String roomTypeId,@Param("y") String contractStatus);
}
