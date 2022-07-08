package com.cognixia.jump.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Orders;
import com.cognixia.jump.repository.OrdersRepository;
import com.cognixia.jump.service.OrdersService;

@RestController
@RequestMapping("/api")
public class OrdersController {
	
	@Autowired
	OrdersService service;
	
	@Autowired
	OrdersRepository repo;

	@GetMapping("/orders")
	public List<Orders> getAllOrders() {
		
		 return service.getAllOrders();
		
	}
	
	@PostMapping("/orders/{id}")
	public ResponseEntity<?> addToCart(@PathVariable int id) {
		
		Orders found = service.addToCart(id);
		
		if (found.getInstrument() == null) {
			return ResponseEntity.status(404).body("Instrument not found.");
		}
		else {
			Orders cartItem = repo.save(found);
			return ResponseEntity.status(201).body(cartItem);
		}
	}
	
	@GetMapping("/cart")
	public ResponseEntity<?> viewCart() {
		
		List<Orders> inCartItems = service.viewCart();
		
		if(inCartItems.isEmpty()) {
			return ResponseEntity.status(404).body("No Items in cart.");
		}
		else {
			return ResponseEntity.status(200).body(inCartItems);
		}
		
	}
	
	@PutMapping("/checkout")
	public ResponseEntity<?> purchaseCart() {
		
		List<Orders> inCartItems = service.viewCart();
		
		if(inCartItems.isEmpty()) {
			return ResponseEntity.status(404).body("No Items in cart.");
		}
		else {
			for(Orders item : inCartItems) {
				item.setStatus("purchased");
				repo.save(item);
			}
			
			return ResponseEntity.status(200).body(inCartItems);
		}
	}
	
	@DeleteMapping("/clear")
	public ResponseEntity<?> clearCart() {
		
		List<Orders> inCartItems = service.viewCart();
		
		if(inCartItems.isEmpty()) {
			return ResponseEntity.status(404).body("No Items in cart.");
		}
		else {
			for(Orders item : inCartItems) {
				repo.deleteById(item.getId());
			}
			
			return ResponseEntity.status(200).body(inCartItems);
		}
	}
	
	
}
