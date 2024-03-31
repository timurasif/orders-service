package com.example.demo.Repositories;

import com.example.demo.Models.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepoInterface extends JpaRepository<OrderEntity, Integer> {
}