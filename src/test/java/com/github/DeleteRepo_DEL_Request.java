package com.github;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class DeleteRepo_DEL_Request extends TestBase {

	@BeforeClass
	public void DeleteRepo() throws InterruptedException {
		setup();
		logger.info("**********Deleting Repo**********");

		//
		// Specify base URI
		RestAssured.baseURI = "https://api.github.com";

		// Request object

		// Include the personal access token in the Authorization header
		httpRequest = RestAssured.given().auth().preemptive().basic("meetp752000",
				"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");

		// Send the request
		response = httpRequest.request(Method.DELETE, "/repos/meetp752000/RestAssureRepo");

		Thread.sleep(2000);
	}

	@Test
	public void CheckResponseBody() {

		logger.info("**********Checking Response Body**********");

		// Process the response
		String responseBody = response.getBody().asString();
		logger.info("Response Body ==>" + responseBody);

	}

	@Test
	public void CheckStatus() {

		logger.info("**********Checking Status**********");

		// Validate status code
		int statusCode = response.getStatusCode();
		logger.info("Status Code ==>" + statusCode);
		Assert.assertEquals(statusCode, 204);

		// Validate status line
		String statusLine = response.getStatusLine();
		logger.info("Status Line ==>" + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 204 No Content");
	}

	@Test
	public void CheckContent() {

		logger.info("**********Check Content**********");

		// Header

		// Content-type
		String contentType = response.header("Content-Type");
		logger.info("Content Type ==>" + contentType);
		Assert.assertEquals(contentType, null);

	}

	@Test
	public void CheckResponseTime() {

		logger.info("**********Check Response Time**********");

		long responseTime = response.getTime();
		logger.info("Response Time ==>" + responseTime);

		if (responseTime > 10000)
			logger.warn("Response Time is greater than 10000");

		Assert.assertTrue(responseTime < 10000);
	}

}
