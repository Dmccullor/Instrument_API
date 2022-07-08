package com.cognixia.jump.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cognixia.jump.model.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Integer> {
	
	Optional<Instrument> findInstrumentByName(String name);

}
