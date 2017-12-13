package APITest.tests;


import org.testng.Assert;
import org.testng.annotations.Test;
import classes.TestAPI;
import classes.User;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UsersTestPutValid {

	//Data fields
	//Data fields
	private RequestSpecification httpRequest;
	private Response response;
	private JsonPath jsonPath;
	private final String id = "thomasbrown";
	private final String first = "First", last = "Last", email = "e@e.com";

	// Set up a request
	// Expected result: httpRequest object is not null
	@Test(priority=1)
	public void UpdateUser() {

		//System.out.println("Setting up the base URL to the RESTful: " + TestAPI.URL); //For debug purposes only

		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = TestAPI.URL + TestAPI.USERS;

		String JsonBody = 
				String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%sm\",\"isactive\":true}", first, last, email);

		//Call RestAssured class to set up a request with the specified base URI
		response = RestAssured.given().contentType("application/json").body(JsonBody).when().put(id);
		System.out.println("Response: => " + response.getBody());

		Assert.assertNotNull(response);
	}


	//Expected: HTTP/1.1 201 Created
	@Test(priority=2, dependsOnMethods = {"UpdateUser"})
	private void GetResponseStatus()
	{
		//int statusCode = response.getStatusCode(); //Get response code
		String statusLine = String.format("%s", response.getStatusLine()); //Get response line

		//Display response code and line:
		//System.out.println("Response Line is =>  " + statusLine); //For debug purposes only

		//Test response code
		Assert.assertEquals(statusLine, "HTTP/1.1 201 Created", "Correct status code returned");		
	}


	// Set up a request
	// Expected result: httpRequest object is not null
	@Test(priority=3, dependsOnMethods = {"GetResponseStatus"})
	public void GetRestAssured() {

		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = TestAPI.URL;

		//Call RestAssured class to set up a request with the specified base URI
		httpRequest = RestAssured.given();

		Assert.assertNotNull(httpRequest);
	}


	@Test(priority=4, dependsOnMethods = {"GetRestAssured"})
	private void GetUpdatedUser()
	{
		//RestAssured class is returning the Request against the base URI
		response = httpRequest.request(Method.GET, (TestAPI.USERS + "/" + id));

		//int statusCode = response.getStatusCode(); //Get response code
		String statusLine = String.format("%s", response.getStatusLine()); //Get response line
		System.out.println("Body => " + response.getBody().asString());

		//Test response code
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK", "Correct status code returned");	
	}


	//Test Status code for: GET /api/users
	//The expected result: HTTP/1.1 200 OK
	//Test user is equal to received user
	@Test(priority=5, dependsOnMethods = {"GetUpdatedUser"})
	public void AssertFname() {					

		//Test is received user equal to test data
		User received = GetUserData();		
		boolean isEqual = false;

		if(received.firstName.equals(first)){

			isEqual = true;
		}

		//System.out.println("Is retriveduser equal to test data: " + isEqual + "\n"); //For debug purposes only
		Assert.assertTrue(isEqual);
	}

	//Test Status code for: GET /api/users
	//The expected result: HTTP/1.1 200 OK
	//Test user is equal to received user
	@Test(priority=5, dependsOnMethods = {"GetUpdatedUser"})
	public void AssertLname() {					

		//Test is received user equal to test data
		User received = GetUserData();		
		boolean isEqual = false;

		if(received.lastName.equals(last)){

			isEqual = true;
		}

		//System.out.println("Is retriveduser equal to test data: " + isEqual + "\n"); //For debug purposes only
		Assert.assertTrue(isEqual);
	}

	//Test Status code for: GET /api/users
	//The expected result: HTTP/1.1 200 OK
	//Test user is equal to received user
	@Test(priority=5, dependsOnMethods = {"GetUpdatedUser"})
	public void AssertEmail() {					

		//Test is received user equal to test data
		User received = GetUserData();		
		boolean isEqual = false;

		if(received.email.equals(email)){

			isEqual = true;
		}

		//System.out.println("Is retriveduser equal to test data: " + isEqual + "\n"); //For debug purposes only
		Assert.assertTrue(isEqual);
	}


	//Test Status code for: GET /api/users
	//The expected result: HTTP/1.1 200 OK
	//Test user is equal to received user
	@Test(priority=5, dependsOnMethods = {"GetUpdatedUser"})
	public void AssertIsActive() {					

		//Test is received user equal to test data
		User received = GetUserData();		
		boolean isEqual = false;

		if(received.isActive == true){

			isEqual = true;
		}

		//System.out.println("Is retriveduser equal to test data: " + isEqual + "\n"); //For debug purposes only
		Assert.assertTrue(isEqual);
	}


	private User GetUserData()
	{
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

