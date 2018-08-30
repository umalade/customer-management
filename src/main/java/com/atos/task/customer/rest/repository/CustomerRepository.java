package com.atos.task.customer.rest.repository;

import org.springframework.stereotype.Repository;

import com.atos.task.customer.rest.domain.Customer;

@Repository
public class CustomerRepository extends InMemoryRepository<Customer> {

	protected void updateIfExists(Customer original, Customer updated) {
		original.setFirstName(updated.getFirstName());
		original.setSurName(updated.getSurName());
	}
}
