package APITest.tests;

import org.testng.annotations.Test;
import classes.TestAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class UsersReset {
	
	// This is not for test. The main purpose is to reset data to initial state
	@Test(priority=1)
	public void ResetUsers() {
									
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = TestAPI.URL + TestAPI.RESET;
						
		// Call RestAssured class to set up a request with the specified base URI
		//RequestSpecification httpRequest = RestAssured.given();
						 		
		String JsonBody = String.format("");
		
		//Call RestAssured class to set up a request with the specified base URI
		Response response = RestAssured.given().contentType("application/json").body(JsonBody).when().post("");
			
		//int statusCode = response.getStatusCode(); //Get response code
		String statusLine = String.format("%s", response.getStatusLine()); //Get response line
										
		//Display response code and line:
		System.out.println("Response Line is =>  " + statusLine); //For debug purposes only
	}
}
