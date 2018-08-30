package com.atos.task.customer.rest.test.acceptance.step;

import java.util.Map;

import org.junit.Assert;

import com.atos.task.customer.rest.test.acceptance.util.AbstractSteps;
import com.fasterxml.jackson.core.type.TypeReference;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CustomerSteps extends AbstractSteps {
	
	private static final String TEST_CUSTOMER = "{\"firstName\": \"Uma\", \"surName\": \"Lade\"}";
	private static final TypeReference<Map<String, Object>> RESOURCE_TYPE = new TypeReference<Map<String, Object>>() {};
	
	@Given("^an customer exists$")
	public void aCustomerExists() throws Throwable {
		createCustomer();
	}
	
	private void createCustomer() throws Exception {
		post("/customer", TEST_CUSTOMER);
	}
	
	@When("^the user creates a customer$")
	public void theUserCallsGetCustomers() throws Throwable {
		createCustomer();
	}
	
	@When("^the user deletes the created customer$")
	public void theUserDeletesTheCreatedCustomer() throws Throwable {
		delete("/customer/{id}", getCreatedId());
	}
	
	private Object getCreatedId() throws Exception {
		return getLastPostContentAs(RESOURCE_TYPE).get("id");
	}
	
	@And("^the customer is successfully created$")
	public void theCustomerIsSuccessfullyCreated() {
		 Assert.assertEquals(201, getLastPostResponse().getStatus());
	}
	
	@And("^the user gets the created customer$")
	public void theUserRetrievesTheCustomer() throws Throwable {
		get("/customer/{id}", getCreatedId());
	}
	
	@Then("^the user receives status code of (\\d+)$")
	public void theUserReceivesStatusCodeOf(int statusCode) throws Throwable {
        Assert.assertEquals(statusCode, getLastStatusCode());
	}
	
	@And("^the retrieved customer is correct$")
	public void theRetrievedCustomerIsCorrect() throws Throwable {
		assertCustomerResourcesMatch(getLastPostContentAs(RESOURCE_TYPE), getLastGetContentAs(RESOURCE_TYPE));
	}
	
	private static void assertCustomerResourcesMatch(Map<String, Object> expected, Map<String, Object> actual) {
		Assert.assertEquals(expected.size(), actual.size());
		
		for (String key: expected.keySet()) {
			Assert.assertEquals(expected.get(key), actual.get(key));
		}
	}
}
