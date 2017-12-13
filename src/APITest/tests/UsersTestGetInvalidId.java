package APITest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import classes.TestAPI;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class UsersTestGetInvalidId {

	//Data fields
	private RequestSpecification httpRequest;
	private Response response;

		
	// Set up a request
	// Expected result: httpRequest object is not null
	@Test(priority=1)
	public void GetRestAssured() {
					
		//System.out.println("Setting up the base URL to the RESTful: " + TestAPI.URL); //For debug purposes only
				
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = TestAPI.URL;
			
		//Call RestAssured class to set up a request with the specified base URI
		httpRequest = RestAssured.given();
			
		Assert.assertNotNull(httpRequest);
	}
		
	
	//Test Status code for: GET /api/users
	//The expected result: 404 Not Found 
	@Test(priority=2, dependsOnMethods = {"GetRestAssured"})
	public void GetInvalidUser() {					
								
		//System.out.println("Test Response for: GET /api/users/:id in case of invalid user"); //For debug purposes only
			
		String id = "noone";
		//System.out.println("User id: " + id); //For debug purposes only
				
		//RestAssured class is returning the Request against the base URI
		response = httpRequest.request(Method.GET, (TestAPI.USERS + "/" + id));
										 						
		//int statusCode = response.getStatusCode(); //Get response code
		String statusLine = String.format("%s", response.getStatusLine()); //Get response line
										
		//Display response code and line:
		//System.out.println("Response Line is =>  " + statusLine); //For debug purposes only
										
		//Test response code
		Assert.assertEquals(statusLine, "HTTP/1.1 405 Method Not Allowed", "Correct status code returned");	
				
		String error = GetError();
		//Test response code
		Assert.assertEquals(error, "404 Not Found", "Correct error code returned");
	}
	
	
	// Test Response Header for: GET /api/users
	// Expected result: application/json; charset=utf-8
	@Test(priority=3, dependsOnMethods = {"GetInvalidUser"})
	public void GetResponseHeader() {
				
		//System.out.println("Test Response Header for: GET /api/users");				 			 								
		
		// Reader header of a give name. 
		// In this line we will get Header named Content-Type
		String contentType = response.header("Content-Type");
		//System.out.println("Content-Type value: " + contentType + "\n"); //For debug purposes only
					
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	//Test Status code for: GET /api/users/:id
	//Expected: 404
	private String GetError()
	{
		// Get response body as a string
		String jsonBody = response.getBody().asString();
		//System.out.println("Error response: " + jsonBody); //For debug purposes only
		return jsonBody;
	}
		

	//End of class
}
