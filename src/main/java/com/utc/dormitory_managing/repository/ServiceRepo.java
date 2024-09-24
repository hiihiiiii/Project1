package com.utc.dormitory_managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utc.dormitory_managing.entity.Service;

public interface ServiceRepo extends JpaRepository<Service, String>  {

}
