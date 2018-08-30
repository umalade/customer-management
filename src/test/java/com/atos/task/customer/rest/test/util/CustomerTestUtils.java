package com.atos.task.customer.rest.test.util;

import org.junit.Assert;

import com.atos.task.customer.rest.domain.Customer;

public class CustomerTestUtils {

	public static void assertAllButIdsMatchBetweenCustomers(Customer expected, Customer actual) {
    	Assert.assertEquals(expected.getFirstName(), actual.getFirstName());
    	Assert.assertEquals(expected.getSurName(), actual.getSurName());
    }
	
    public static Customer generateTestCustomer() {
    	Customer customer = new Customer();
    	customer.setFirstName("TestFirstName");
    	customer.setSurName("TestSurName");
    	return customer;
    }
    
     public static Customer generateUpdatedCustomer(Customer original) {
    	Customer updated = new Customer();
    	updated.setFirstName(original.getFirstName() + " updated");
    	updated.setSurName(original.getSurName() + 100);
    	return updated;
    }
}
