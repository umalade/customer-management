package com.atos.task.customer.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import com.atos.task.customer.rest.domain.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerResource extends ResourceSupport {

	private final long id;
	private final String firstName;
	private final String surName;
	
	public CustomerResource(Customer customer) {
		id = customer.getId();
		firstName = customer.getFirstName();
		surName = customer.getSurName();
	}

	@JsonProperty("id")
	public Long getResourceId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurName() {
		return surName;
	}
	
}
