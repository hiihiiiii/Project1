package com.utc.dormitory_managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utc.dormitory_managing.entity.Contract;

public interface ContractRepo extends JpaRepository<Contract, String>  {

}
