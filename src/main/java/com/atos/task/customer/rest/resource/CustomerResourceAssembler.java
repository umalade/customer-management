package com.atos.task.customer.rest.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import com.atos.task.customer.rest.domain.Customer;

@Component
public class CustomerResourceAssembler extends ResourceAssembler<Customer, CustomerResource> {
	
	@Autowired
	protected EntityLinks entityLinks;

	private static final String UPDATE_REL = "update";
	private static final String DELETE_REL = "delete";

	@Override
	public CustomerResource toResource(Customer customer) {
		
		CustomerResource resource = new CustomerResource(customer);
		
		final Link selfLink = entityLinks.linkToSingleResource(customer);
		
		resource.add(selfLink.withSelfRel());
		resource.add(selfLink.withRel(UPDATE_REL));
		resource.add(selfLink.withRel(DELETE_REL));
		
		return resource;
	}
}
