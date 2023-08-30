package com.github;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;

public class CreateANewRepo_POST_Request extends TestBase {

	@BeforeClass
	void createRepo() throws InterruptedException {
		setup();

		logger.info("**********Starting Creation Of Repo**********");

		// Specify base URI
		RestAssured.baseURI = "https://api.github.com";

		// Request object

		// Include the personal access token in the Authorization header
		httpRequest = RestAssured.given().auth().preemptive().basic("meetp752000",
				"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");

		// Request payload
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "RestAssureRepo");
		requestParams.put("description", "This is your first repository");
		requestParams.put("homepage", "https://github.com");
		requestParams.put("private", false);
		requestParams.put("has_issues", true);
		requestParams.put("has_projects", true);

		// Set content type and request body
		httpRequest.contentType(ContentType.JSON).body(requestParams.toString());

		response = httpRequest.request(Method.POST, "/user/repos");

		Thread.sleep(2000);
	}

	@Test
	public void PrintResponseBody() {

		logger.info("**********Checking Response Body**********");
		// Process the response and print
		String responseBody = response.getBody().asString();
		logger.info("Response Body ==>" + responseBody);
		System.out.println("Response Body is:" + responseBody);
	}

	@Test
	public void CheckStatus() {
		logger.info("**********Checking Status**********");

		// Validate status code
		int statusCode = response.getStatusCode();
		System.out.println("status code is: " + statusCode);

		if (statusCode == 422) {

			System.out.println("The Repo is already created. Cant Create the same repo");
			logger.info("Status Code ==>" + statusCode);
			Assert.assertEquals(statusCode, 422);

		} else if (statusCode == 201) {

			System.out.println("Created a new repo");
			logger.info("Status Code ==>" + statusCode);
			Assert.assertEquals(statusCode, 201);

		}

		// Validate status line
		String statusLine = response.getStatusLine();
		logger.info("Status Line ==>" + statusLine);
		System.out.println(statusLine);

		if (statusCode == 422) {

			Assert.assertEquals(statusLine, "HTTP/1.1 422 Unprocessable Entity");

			// print JSON values from body
			String message = response.jsonPath().get("message");
			System.out.println("message: " + message);
			Assert.assertEquals(message, "Repository creation failed.");

		} else if (statusCode == 201) {

			Assert.assertEquals(statusLine, "HTTP/1.1 201 Created");
			logger.info("Status Line ==>" + statusLine);
		}

	}

	@Test
	public void TestHeader() {

		logger.info("**********Header**********");

		// Header

		// Content-type
		String contentType = response.header("Content-Type");
		System.out.println("Content Type is: " + contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");

		// prints all headers
		Headers allHeaders = response.headers(); // captures all the headers from response

		for (Header header : allHeaders) {
			System.out.println(header.getName() + "      " + header.getValue());
			logger.info("Header ==>" + header);
		}
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
