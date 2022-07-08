package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Instrument;
import com.cognixia.jump.repository.InstrumentRepository;
import com.cognixia.jump.service.InstrumentService;

@RestController
@RequestMapping("/api")
public class InstrumentController {
	
	@Autowired
	InstrumentRepository repo;
	
	@Autowired
	InstrumentService service;
	
	@GetMapping("/instrument")
	public List<Instrument> getAllInstruments() {
		return repo.findAll();
	}
	
	@PostMapping("/instrument")
	public ResponseEntity<Instrument> createInstrument(@Valid @RequestBody Instrument instrument) {
		
		instrument.setId(-1);
		
		Instrument created = repo.save(instrument);
		
		return ResponseEntity.status(201).body(created);
		
	}
	
	@GetMapping("/instrument/{id}")
	public ResponseEntity<?> getInstrument(@PathVariable int id) {
		
		Optional<Instrument> found = repo.findById(id);
		
		if(found == null) {
			return ResponseEntity.status(404).body("Instrument not found");
		} 
		
		else {
			return ResponseEntity.status(200).body(found);
		}
		
	}
	
	@DeleteMapping("/instrument/{id}")
	public ResponseEntity<?> deleteInstrument(@PathVariable int id) {
		
		Optional<Instrument> toDelete = repo.findById(id);
		
		if(toDelete == null) {
			return ResponseEntity.status(400).body("Nothing to delete");
		}
		else {
			repo.deleteById(id);
			return ResponseEntity.status(200).body("Instrument with id " + id + " deleted");
		}
		
	}
	
	@PutMapping("/instrument")
	public ResponseEntity<?> updateInstrument(@RequestBody Instrument instrument) {
		Optional<Instrument> toUpdate = repo.findById(instrument.getId());
		
		if(toUpdate == null) {
			return ResponseEntity.status(404).body("Instrument not found.");
		}
		else {
			Instrument updated = repo.save(service.updateInstrument(instrument));
			return ResponseEntity.status(200).body(updated);
		}
	}
	

}
