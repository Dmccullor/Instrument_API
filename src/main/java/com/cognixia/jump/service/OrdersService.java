package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Instrument;
import com.cognixia.jump.model.Orders;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.InstrumentRepository;
import com.cognixia.jump.repository.OrdersRepository;
import com.cognixia.jump.repository.UserRepository;

@Service
public class OrdersService {
	
	@Autowired
	OrdersRepository ordersRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	InstrumentRepository instrumentRepo;
	

	
	public List<Orders> getAllOrders() {
		return ordersRepo.findAll();
	}
	
	public Orders addToCart(int id) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		
		Optional<User> nullableUser = userRepo.findByUsername(username);
		Optional<Instrument> nullableInstrument = instrumentRepo.findById(id);
		
		if(nullableUser == null || nullableInstrument == null) {
			Orders cartItem = new Orders();
			return cartItem;
		}
		else {
			User thisUser = nullableUser.get();
			Instrument thisInstrument = nullableInstrument.get();
			Orders cartItem = new Orders();
			cartItem.setUser(thisUser);
			cartItem.setInstrument(thisInstrument);
			cartItem.setStatus("cart");
		
		return cartItem;
		}
		
	}
	

	
	public List<Orders> viewCart() {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		String username = userDetails.getUsername();
		
		List<Orders> inCartItems = ordersRepo.viewCart(username);
			
		return inCartItems;
		}
		
}
