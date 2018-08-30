package com.atos.task.customer.rest.test.integration.controller.util;

import static com.atos.task.customer.rest.test.integration.controller.util.ControllerTestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.hateoas.EntityLinks;
import org.springframework.test.web.servlet.ResultMatcher;

import com.atos.task.customer.rest.domain.Customer;

public class CustomerControllerTestUtils {

	public static ResultMatcher customerAtIndexIsCorrect(int index, Customer expected) {
		return new CompositeResultMatcher()
			.addMatcher(jsonPath("$.[" + index + "].id").value(expected.getId()))
			.addMatcher(jsonPath("$.[" + index + "].firstName").value(expected.getFirstName()))
			.addMatcher(jsonPath("$.[" + index + "].surName").value(expected.getSurName()));
	}
	
	public static ResultMatcher customerIsCorrect(Customer expected) {
		return customerIsCorrect(expected.getId(), expected);
	}
	
	private static ResultMatcher customerIsCorrect(Long expectedId, Customer expected) {
		return new CompositeResultMatcher().addMatcher(jsonPath("$.id").value(expectedId))
			.addMatcher(jsonPath("$.firstName").value(expected.getFirstName()))
			.addMatcher(jsonPath("$.surName").value(expected.getSurName()));
	}
	
	public static ResultMatcher updatedCustomerIsCorrect(Long originalId, Customer expected) {
		return customerIsCorrect(originalId, expected);
	}
	
	public static ResultMatcher customerLinksAtIndexAreCorrect(int index, Customer expected, EntityLinks entityLinks) {
		final String selfReference = entityLinks.linkForSingleResource(expected).toString();
		
		return new CompositeResultMatcher()
			.addMatcher(selfLinkAtIndexIs(index, selfReference))
			.addMatcher(updateLinkAtIndexIs(index, selfReference))
			.addMatcher(deleteLinkAtIndexIs(index, selfReference));
	}
	
	public static ResultMatcher customerLinksAreCorrect(Customer expected, EntityLinks entityLinks) {
		final String selfReference = entityLinks.linkForSingleResource(expected).toString();
		
		return new CompositeResultMatcher()
			.addMatcher(selfLinkIs(selfReference))
			.addMatcher(updateLinkIs(selfReference))
			.addMatcher(deleteLinkIs(selfReference));
	}
}
