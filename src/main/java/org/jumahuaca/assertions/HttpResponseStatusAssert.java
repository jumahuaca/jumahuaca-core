package org.jumahuaca.assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.AbstractAssert;

public class HttpResponseStatusAssert extends AbstractAssert<HttpResponseStatusAssert,HttpServletResponse> {
	
	public HttpResponseStatusAssert(HttpServletResponse actual) {
		super(actual, HttpResponseStatusAssert.class);
	}
	
	public static void assertResponse200(HttpServletResponse actual) {
		assertResponse(actual, 200);
	}
	
	public static void assertResponse404(HttpServletResponse actual) {
		assertResponse(actual, 404);
	}
	
	public static void assertResponse500(HttpServletResponse actual) {
		assertResponse(actual, 500);
	}
	
	private static void assertResponse(HttpServletResponse actual, int statusCode) {
		assertThat(actual).isNotNull();
		if(actual.getStatus()!=statusCode) {
			fail("Expected status code: " + statusCode + " but found: " + actual.getStatus());
		}
	}
	
	

	
	

}
