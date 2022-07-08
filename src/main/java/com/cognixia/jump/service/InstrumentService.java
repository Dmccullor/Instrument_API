package com.cognixia.jump.service;

import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Instrument;

@Service
public class InstrumentService {
	
	public Instrument updateInstrument(Instrument instrument) {
		
		Instrument updated = new Instrument(instrument.getId(), instrument.getName(), instrument.getType(), instrument.getPrice(), instrument.getQty());
		return updated;
		
	}

}
