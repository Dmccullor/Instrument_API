package com.cognixia.jump.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cognixia.jump.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

	@Query(value = "SELECT * FROM orders o WHERE o.username = ?", nativeQuery = true)
	public List<Orders> itemsInCart(String username);
	
	@Query(value = "SELECT * FROM orders o WHERE o.username = ? AND o.status = 'cart'", nativeQuery = true)
	public List<Orders> viewCart(String username);
}
