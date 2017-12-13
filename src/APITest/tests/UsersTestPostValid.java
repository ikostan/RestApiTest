package APITest.tests;

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.Test;
import classes.TestAPI;
import classes.User;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


public class UsersTestPostValid {

	//Data fields
	private Response response;
	private JsonPath jsonPath;

	Date datePriorCreation;

	private final String fName = "First", lName = "Last", email ="f.last@company.com";

	// Set up a request
	// Expected result: httpRequest object is not null
	@Test(priority=1)
	public void CreateNewUser() {

		//System.out.println("Setting up the base URL to the RESTful: " + TestAPI.URL); //For debug purposes only

		// Specify the base URL to the RESTful web service
		RestAssured.baseURI = TestAPI.URL + TestAPI.USERS;

		String JsonBody = String.format("{\"firstName\":\"%s\",\"lastName\":\"%s\",\"email\":\"%sm\"}", fName, lName, email);

		datePriorCreation = new Date();

		//Call RestAssured class to set up a request with the specified base URI
		response = RestAssured.given().contentType("application/json").body(JsonBody).when().post("");

		Assert.assertNotNull(response);
	}


	//Expected: HTTP/1.1 201 Created
	@Test(priority=2, dependsOnMethods = {"CreateNewUser"})
	private void GetResponseStatus()
	{
		//int statusCode = response.getStatusCode(); //Get response code
		String statusLine = String.format("%s", response.getStatusLine()); //Get response line

		//Display response code and line:
		//System.out.println("Response Line is =>  " + statusLine); //For debug purposes only

		//Test response code
		Assert.assertEquals(statusLine, "HTTP/1.1 201 Created", "Correct status code returned");		
	}


	//Create a new user by sending POST request
	@Test(priority=3, dependsOnMethods = {"GetResponseStatus"})
	public void AssertUserId()
	{
		//String body = response.getBody().asString();
		//System.out.println("Response body: " + body); //Debug only

		User received = GetUserData();

		boolean isEqual = false;

		//System.out.println(String.format("Retreived user id: %s", received.id)); //Debug only

		if(received.id.equals(fName.toLowerCase() + lName.toLowerCase())){

			isEqual = true;
		}

		Assert.assertTrue(isEqual);
	}


	//Assert email
	@Test(priority=3, dependsOnMethods = {"GetResponseStatus"})
	public void AssertUserEmail()
	{
		//String body = response.getBody().asString();
		//System.out.println("Response body: " + body); //Debug only

		User received = GetUserData();

		boolean isEqual = false;

		//System.out.println(String.format("Retreived user data: %s, %s, %s", received.firstName, received.lastName, received.email)); //Debug only

		if(received.email.equals(email)){

			isEqual = true;
		}

		Assert.assertTrue(isEqual);
	}

	//Assert first name
	@Test(priority=4, dependsOnMethods = {"AssertUserId"})
	public void AssertUserFirstName()
	{
		//String body = response.getBody().asString();
		//System.out.println("Response body: " + body); //Debug only

		User received = GetUserData();

		boolean isEqual = false;

		//System.out.println(String.format("Retreived user data: %s, %s, %s", received.firstName, received.lastName, received.email)); //Debug only

		if(received.firstName.equals(fName)){

			isEqual = true;
		}

		Assert.assertTrue(isEqual);
	}


	//Assert last name
	@Test(priority=4, dependsOnMethods = {"AssertUserId"})
	public void AssertUserLastName()
	{
		//String body = response.getBody().asString();
		//System.out.println("Response body: " + body); //Debug only

		User received = GetUserData();

		boolean isEqual = false;

		//System.out.println(String.format("Retreived user data: %s, %s, %s", received.firstName, received.lastName, received.email)); //Debug only

		if(received.lastName.equals(lName)){

			isEqual = true;
		}

		Assert.assertTrue(isEqual);
	}

	//Assert "created" date
	@Test(priority=4, dependsOnMethods = {"AssertUserId"})
	public void AssertCreatedDate()
	{
		Date dateAfterCreation = new Date();

		User received = GetUserData();

		boolean isValid = false;

		System.out.println("Date before: " + datePriorCreation + ", created: " + received.created + ", after: " + dateAfterCreation);

		if(received.created.after(datePriorCreation) && received.created.before(dateAfterCreation)){

			isValid = true;
		}

		Assert.assertTrue(isValid);
	}

	//Assert "lastLogin" date
	@Test(priority=4, dependsOnMethods = {"AssertUserId"})
	public void AssertLastLogin()
	{
		User received = GetUserData();

		boolean isValid = false;

		System.out.println("Date lastLogin: " + received.lastLogin);

		if(received.lastLogin.equals(new Date(0))){

			isValid = true;
		}

		Assert.assertTrue(isValid);
	}

	//Assert "lastLogin" date
	@Test(priority=4, dependsOnMethods = {"AssertUserId"})
	public void AssertIsActive()
	{
		User received = GetUserData();

		boolean isValid = false;

		if(!received.isActive){

			isValid = true;
		}

		Assert.assertTrue(isValid);
	}


	// Test Response Header for: POST /api/users
	// Expected result: application/json; charset=utf-8
	@Test(priority=4, dependsOnMethods = {"AssertUserId"})
	public void GetResponseHeader() {

		//System.out.println("Test Response Header for: POST /api/users");				 			 								

		// Reader header of a give name. 
		// In this line we will get Header named Content-Type
		String contentType = response.header("Content-Type");
		//System.out.println("Content-Type value: " + contentType + "\n"); //For debug purposes only

		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}


	//Test Status code for: GET /api/users
	//The expected result: 7 specific users of Type User
	//@Test(priority=4, dependsOnMethods = {"GetResponseBody"})
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

		//System.out.println(String.format("Retreived user data: %s, %s, %s", received.firstName, received.lastName, received.email)); //Debug only

		return received;
	}

	//End of class
}
