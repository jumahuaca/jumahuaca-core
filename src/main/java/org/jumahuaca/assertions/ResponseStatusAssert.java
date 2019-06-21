package org.jumahuaca.assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import javax.ws.rs.core.Response;

import org.assertj.core.api.AbstractAssert;

public class ResponseStatusAssert extends AbstractAssert<ResponseStatusAssert,Response> {
	
	public ResponseStatusAssert(Response actual) {
		super(actual, ResponseStatusAssert.class);
	}
	
	public static void assertResponse200(Response actual) {
		assertResponse(actual, 200);
	}
	
	public static void assertResponse404(Response actual) {
		assertResponse(actual, 404);
	}
	
	public static void assertResponse500(Response actual) {
		assertResponse(actual, 500);
	}
	
	private static void assertResponse(Response actual, int statusCode) {
		assertThat(actual).isNotNull();
		if(actual.getStatus()!=statusCode) {
			fail("Expected status code: " + statusCode + " but found: " + actual.getStatus());
		}
	}
	
	

	
	

}
