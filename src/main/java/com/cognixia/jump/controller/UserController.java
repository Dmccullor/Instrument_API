package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.AuthenticationRequest;
import com.cognixia.jump.model.AuthenticationResponse;
import com.cognixia.jump.model.User;
import com.cognixia.jump.repository.UserRepository;
import com.cognixia.jump.service.MyUserDetailsService;
import com.cognixia.jump.service.UserService;
import com.cognixia.jump.util.JwtUtil;

@RestController
@RequestMapping("/api")
public class UserController{
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserService service;
	
	@Autowired
	UserRepository repo;
	
	@Autowired
	PasswordEncoder encoder;

	@GetMapping("/user")
	public List<User> getAllUsers() {
		
		return repo.findAll();
		
	}
	
	@PostMapping("/user")
	public ResponseEntity<?> createUser( @Valid @RequestBody User user ) {
		
		user.setId(null);
		
		user.setPassword( encoder.encode( user.getPassword() ) );
		
		User created = repo.save(user);
		
		return ResponseEntity.status(201).body(created);
		
	}
	
	@PostMapping("/authenticate") 
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest request) throws Exception {
		
		try {
			authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()) );
			
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password");
		}
		

		final UserDetails userDetails = userDetailsService.loadUserByUsername( request.getUsername() );
		
		final String jwt = jwtUtil.generateTokens(userDetails);
		
		return ResponseEntity.status(201).body( new AuthenticationResponse(jwt) );
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUserById(@PathVariable int id) {
		
		Optional<User> found = repo.findById(id);
		
		if(found == null) {
			return ResponseEntity.status(404).body("User not found");
		}
		else {
			return ResponseEntity.status(200).body(found);
		}
		
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		
		Optional<User> toDelete = repo.findById(id);
		
		if(toDelete == null) {
			return ResponseEntity.status(204).body("No one to delete");
		}
		else {
			repo.deleteById(id);
			return ResponseEntity.status(204).body("User with id of " + id + " has been deleted.");
		}
		
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		Optional<User> toUpdate = repo.findById(user.getId());
		
		if(toUpdate == null) {
			return ResponseEntity.status(404).body("User not found.");
		}
		else {
			User updated = repo.save(service.updateUser(user));
			return ResponseEntity.status(200).body(updated);
		}
	}
}
