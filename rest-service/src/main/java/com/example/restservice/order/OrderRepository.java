package com.example.restservice.order;

import com.example.restservice.material.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o WHERE o.transId = ?1")
    Optional<Order> findOrderByTransId(Integer transId);
}
