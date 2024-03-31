package com.example.demo.Repositories;

import com.example.demo.Models.Entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepoInterface extends JpaRepository<OrderEntity, Integer> {

    @Query(value = "SELECT *\n" +
            "FROM Orders AS o\n" +
            "Order by o.id\n" +
            "LIMIT ?1 ;", nativeQuery = true)
    List<OrderEntity> getFirstNOrders(int n);

}