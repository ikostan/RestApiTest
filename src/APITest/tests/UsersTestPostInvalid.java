package APITest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import classes.TestAPI;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class UsersTestPostInvalid {
	
	//Data fields
	//private Response httpRequest;
	private Response response;

	private final String[] InvalidData = new String[]{"-a", "231@", "", " ", "a", "a s", "asdfghjklqwertyuiopzxcvbasdfghjklqwertyuiopzxcvbasdfghjklqwertyuiopzxcvb@company.com"};

	
	// Set up a request
	// Expected result: httpRequest object is not null
	@Test(priority=1)
	public void CreateEmptyUser() {
					
		//System.out.println("Setting up the base URL to the RESTful: " + TestAPI.URL); //For debug purposes only
				
		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = TestAPI.URL + TestAPI.USERS;
		
		for(String str : InvalidData)
		{
			//String JsonBody = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%sm\"}", EmptyFname, EmptyLname, EmptyMail);
			
			String JsonBody = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%sm\"}", str, str, str);
			
			//Call RestAssured class to set up a request with the specified base URI
			response = RestAssured.given().contentType("application/json").body(JsonBody).when().post("");
				
			Assert.assertNotNull(response);
			
			GetResponseStatus();
			GetResponseHeader();
		}
	}
	
	
	//Expected: HTTP/1.1 400 Bad Request
	//@Test(priority=2, dependsOnMethods = {"CreateEmptyUser"})
	private void GetResponseStatus()
	{
		//int statusCode = response.getStatusCode(); //Get response code
		String statusLine = String.format("%s", response.getStatusLine()); //Get response line
								
		//Display response code and line:
		//System.out.println("Response Line is =>  " + statusLine); //For debug purposes only
								
		//Test response code
		Assert.assertEquals(statusLine, "HTTP/1.1 400 Bad Request", "Correct status code returned");		
	}
	
		
	// Test Response Header for: POST /api/users
	// Expected result: application/json; charset=utf-8
	//@Test(priority=3, dependsOnMethods = {"GetResponseStatus"})
	public void GetResponseHeader() {
					
		//System.out.println("Test Response Header for: POST /api/users");				 			 								
			
		// Reader header of a give name. 
		// In this line we will get Header named Content-Type
		String contentType = response.header("Content-Type");
		//System.out.println("Content-Type value: " + contentType + "\n"); //For debug purposes only
						
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
		
	
	//End of class
}
