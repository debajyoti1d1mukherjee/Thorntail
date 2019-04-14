package com.poc.thorntail.dbentities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="greeting")
public class GreetingEntity {
	
	@Id
	@Column(name = "id")
	public int id;
	@Column(name = "greeting")
	public String greeting;
	
	public GreetingEntity() {}
	
	public GreetingEntity(int id, String greeting) {
		this.id = id;
		this.greeting=greeting;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	
}
