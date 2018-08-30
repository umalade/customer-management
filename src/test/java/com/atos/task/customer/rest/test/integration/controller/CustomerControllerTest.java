package com.atos.task.customer.rest.test.integration.controller;

import static com.atos.task.customer.rest.test.integration.controller.util.CustomerControllerTestUtils.*;
import static com.atos.task.customer.rest.test.util.CustomerTestUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityLinks;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.atos.task.customer.rest.domain.Customer;
import com.atos.task.customer.rest.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest extends ControllerIntegrationTest {
	
	private static final String INVALID_TEST_CUSTOMER = "";
	private static final String TEST_CUSTOMER = "{\"firstName\": \"test firstname\", \"surname\": \"test surname\"}";
	private static final String TEST_CUSTOMER_MISSING_DATA = "{\"foo\": \"bar\"}";
	
	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private EntityLinks entityLinks;
	
	@Before
	public void setUp() {
		repository.clear();
	}

    @Test
    public void testGetAllEmptyListEnsureCorrectResponse() throws Exception {
    	assertNoCustomers();
        getCustomer()
        	.andExpect(status().isOk())
            .andExpect(content().string(equalTo("[]")));
    }

	private ResultActions getCustomer() throws Exception {
		return get("/customer");
	}
    
    private void assertNoCustomers() {
    	assertCustomerCountIs(0);
    }
    
    private void assertCustomerCountIs(int count) {
    	Assert.assertEquals(count, repository.getCount());
    }
    
    @Test
    public void testGetAllOneCustomerEnsureCorrectResponse() throws Exception {
    	Customer injectedCustomer = injectCustomer();
    	assertCustomerCountIs(1);
        getCustomer()
        	.andExpect(status().isOk())
        	.andExpect(customerAtIndexIsCorrect(0, injectedCustomer));
    }
    
    @Test
    public void testGetAllOneCustomerEnsureCorrectLinks() throws Exception {
    	Customer injectedCustomer = injectCustomer();
    	assertCustomerCountIs(1);
        getCustomer()
        	.andExpect(status().isOk())
        	.andExpect(customerLinksAtIndexAreCorrect(0, injectedCustomer, entityLinks));
    }
    
    private Customer injectCustomer() {
    	Customer customer = new Customer();
    	customer.setFirstName("Test FirstName");
    	customer.setSurName("Test SurName");
    	
    	return repository.create(customer);
    }
    
    @Test
    public void testGetAllTwoCustomerEnsureCorrectResponse() throws Exception {
    	Customer injectedCustomer1 = injectCustomer();
    	Customer injectedCustomer2 = injectCustomer();
    	assertCustomerCountIs(2);
        getCustomer()
        	.andExpect(status().isOk())
        	.andExpect(customerAtIndexIsCorrect(0, injectedCustomer1))
        	.andExpect(customerAtIndexIsCorrect(1, injectedCustomer2));
    }
    
    @Test
    public void testGetAllTwoCustomerEnsureCorrectLinks() throws Exception {
    	Customer injectedCustomer1 = injectCustomer();
    	Customer injectedCustomer2 = injectCustomer();
    	assertCustomerCountIs(2);
    	getCustomer()
	    	.andExpect(status().isOk())
	    	.andExpect(customerLinksAtIndexAreCorrect(0, injectedCustomer1, entityLinks))
	    	.andExpect(customerLinksAtIndexAreCorrect(1, injectedCustomer2, entityLinks));
    }
    
    @Test
    public void testGetNonexistentCustomerEnsureNotFoundResponse() throws Exception {
    	assertNoCustomers();
        getCustomer(1)
        	.andExpect(status().isNotFound());
    }

	private ResultActions getCustomer(long id) throws Exception {
		return get("/customer/{id}", id);
	}
    
    @Test
    public void testGetExistingCustomerEnsureCorrectResponse() throws Exception {
    	Customer injectedCustomer = injectCustomer();
    	assertCustomerCountIs(1);
        getCustomer(injectedCustomer.getId())
        	.andExpect(status().isOk())
        	.andExpect(customerIsCorrect(injectedCustomer));
    }
    
    @Test
    public void testGetExistingOrderEnsureCorrectLinks() throws Exception {
    	Customer injectedCustomer = injectCustomer();
    	assertCustomerCountIs(1);
    	getCustomer(injectedCustomer.getId())
        	.andExpect(status().isOk())
        	.andExpect(customerLinksAreCorrect(injectedCustomer, entityLinks));
    }
    
    @Test
    public void testCreateNewCustomerEnsureCustomerCreated() throws Exception {
    	assertNoCustomers();
    	Customer desiredCustomer = generateTestCustomer();
    	createCustomer(toJsonString(desiredCustomer));
    	assertCustomerCountIs(1);
    	assertAllButIdsMatchBetweenCustomers(desiredCustomer, getCreatedCustomer());
    }
    
    private ResultActions createCustomer(String payload) throws Exception {
    	return post("/customer", payload);
    }

	private Customer getCreatedCustomer() {
		List<Customer> customers = repository.findAll();
		return customers.get(customers.size() - 1);
	}
    
    @Test
    public void testCreateNewCustomerEnsureCorrectResponse() throws Exception {
    	assertNoCustomers();
    	createCustomer(TEST_CUSTOMER)
    		.andExpect(status().isCreated())
    		.andExpect(customerIsCorrect(getCreatedCustomer()));
    }
    
    @Test
    public void testCreateNewCustomerEnsureCorrectLinks() throws Exception {
    	assertNoCustomers();
    	createCustomer(TEST_CUSTOMER)
    		.andExpect(status().isCreated())
    		.andExpect(customerLinksAreCorrect(getCreatedCustomer(), entityLinks));
    }
    
    @Test
    public void testCreateNewCustomerMissingDataEnsureCorrectResponse() throws Exception {
    	assertNoCustomers();
    	createCustomer(TEST_CUSTOMER_MISSING_DATA)
    		.andExpect(status().isCreated())
    		.andExpect(customerIsCorrect(getCreatedCustomer()));
    }
    
    @Test
    public void testCreateInvalidNewCustomerEnsureCorrectResponse() throws Exception {
    	assertNoCustomers();
    	createCustomer(INVALID_TEST_CUSTOMER)
    		.andExpect(status().isBadRequest());
    }
    
    @Test
    public void testDeleteNonexistentCustomerEnsureCorrectResponse() throws Exception {
    	assertNoCustomers();
    	deleteCustomer(1)
    		.andExpect(status().isNotFound());
    }
    
    private ResultActions deleteCustomer(long id) throws Exception {
    	return delete("/customer/{id}", id);
    }

    @Test
    public void testDeleteExistingCustomerEnsureCorrectResponse() throws Exception {
    	Customer injectedCustomer = injectCustomer();
    	assertCustomerCountIs(1);
    	deleteCustomer(injectedCustomer.getId())
    		.andExpect(status().isNoContent());
    }
    
    @Test
    public void testDeleteExistingCustomerEnsureCustomerDeleted() throws Exception {
    	Customer injectedCustomer = injectCustomer();
    	assertCustomerCountIs(1);
    	deleteCustomer(injectedCustomer.getId());
    	assertNoCustomers();
    }
    
    @Test
    public void testUpdateNonexistentCustomerEnsureCorrectResponse() throws Exception {
    	assertNoCustomers();
    	updateCustomer(1, new Customer())
    		.andExpect(status().isNotFound());
    }
    
    private ResultActions updateCustomer(long id, Customer updatedCustomer) throws Exception {
    	return put("/customer/{id}", updatedCustomer, String.valueOf(id));
    }
    
    @Test
    public void testUpdateExistingCustomerEnsureCustomerUpdated() throws Exception {
    	Customer originalCustomer = injectCustomer();
    	assertCustomerCountIs(1);
    	Customer updatedCustomer = generateUpdatedCustomer(originalCustomer);
    	updateCustomer(originalCustomer.getId(), updatedCustomer);
    	assertAllButIdsMatchBetweenCustomers(updatedCustomer, originalCustomer);
    }
    
    @Test
    public void testUpdateExistingCustomerEnsureCorrectResponse() throws Exception {
    	Customer originalCustomer = injectCustomer();
    	assertCustomerCountIs(1);
    	Customer updatedCustomer = generateUpdatedCustomer(originalCustomer);
    	updateCustomer(originalCustomer.getId(), updatedCustomer)
    		.andExpect(status().isOk())
    		.andExpect(updatedCustomerIsCorrect(originalCustomer.getId(), updatedCustomer));
    }
}
