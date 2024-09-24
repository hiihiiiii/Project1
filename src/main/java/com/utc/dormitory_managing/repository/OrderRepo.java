package com.utc.dormitory_managing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utc.dormitory_managing.entity.Order;

public interface OrderRepo extends JpaRepository<Order, String>  {

}
