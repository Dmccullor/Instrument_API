package com.cognixia.jump.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Instrument {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false)
	private String name;
	
	@Column
	private String type;
	
	@Column(nullable=false)
	private double price;
	
	@Column(nullable=false)
	private int qty;
	
	public Instrument() {
		this(-1, "N/A", "N/A", 0, 0);
	}
	
	public Instrument(Integer id, String name, String type, double price, int qty) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.qty = qty;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "Instrument [id=" + id + ", name=" + name + ", type=" + type + ", price=" + price + ", qty=" + qty + "]";
	}
	
	

}
