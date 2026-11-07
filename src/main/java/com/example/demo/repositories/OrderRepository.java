package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Orders;
import com.example.demo.entities.User;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
	List<Orders> findOrdersByUser(User user);

	@Query("SELECT o FROM Orders o WHERE o.user.u_id = :userId")
	List<Orders> findOrdersByUserId(@Param("userId") int userId);
}
