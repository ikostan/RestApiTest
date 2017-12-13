package APITest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import classes.AllUsers;
import classes.TestAPI;
import classes.User;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class UsersTestGetValidId {

	//Data fields
	private RequestSpecification httpRequest;
	private Response response;
	private JsonPath jsonPath;

		
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
	//The expected result: HTTP/1.1 200 OK
	//Test user is equal to received user
	@Test(priority=2, dependsOnMethods = {"GetRestAssured"})
	public void AssertRetreivedUsers() {					
							
		//System.out.println("Test Response for: GET /api/users/:id"); //For debug purposes only
		
		//Instantiate class with test data
		AllUsers testUsers = new AllUsers();
		
		for(User usr : testUsers.getUsers())
		{
			String id = usr.id;
			//System.out.println("User id: " + usr.id); //For debug purposes only
			
			//RestAssured class is returning the Request against the base URI
			response = httpRequest.request(Method.GET, (TestAPI.USERS + "/" + id));
									 						
			//int statusCode = response.getStatusCode(); //Get response code
			String statusLine = String.format("%s", response.getStatusLine()); //Get response line
			System.out.println("Body => " + response.getBody().asString());
									
			//Display response code and line:
			//System.out.println("Response Line is =>  " + statusLine); //For debug purposes only
									
			//Test response code
			Assert.assertEquals(statusLine, "HTTP/1.1 200 OK", "Correct status code returned");	
						
			//Test is received user equal to test data
			User received = GetUserData();		
			boolean isEqual = false;
			
			if(usr.isSameUser(received)){
				
				isEqual = true;
			}
			
			//System.out.println("Is retriveduser equal to test data: " + isEqual + "\n"); //For debug purposes only
			Assert.assertTrue(isEqual);
			
			//GetResponseHeader();
		}			
	}
		
	
	// Test Response Header for: GET /api/users
	// Expected result: application/json; charset=utf-8
	@Test(priority=3, dependsOnMethods = {"AssertRetreivedUsers"})
	public void GetResponseHeader() {
				
		//System.out.println("Test Response Header for: GET /api/users");				 			 								
		
		// Reader header of a give name. 
		// In this line we will get Header named Content-Type
		String contentType = response.header("Content-Type");
		//System.out.println("Content-Type value: " + contentType + "\n"); //For debug purposes only
					
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	
	private User GetUserData()
	{
		//System.out.println("Test retreived users for: GET /api/users"); //For debug purposes only

		// Get the JsonPath object instance from the Response interface
		jsonPath = response.jsonPath();
		
		String userFirstName = jsonPath.get("firstName");
		String userLastName = jsonPath.get("lastName");
		Boolean userIsActive = jsonPath.get("isactive");
		String userId = jsonPath.get("id");
		String userCreated = jsonPath.get("created");
		String userLastLogin = jsonPath.get("lastLogin").toString();
		String userEmail = jsonPath.get("email");
				

		//String fName, String lName, String newEmail, String newId, boolean newIsActive, String newCreated, long newLastLogin
		User received = new User(userFirstName,
									userLastName,
									userEmail,
									userId,
									userIsActive,
									userCreated,
									userLastLogin);
		
		return received;
	}

	//End of class
}
