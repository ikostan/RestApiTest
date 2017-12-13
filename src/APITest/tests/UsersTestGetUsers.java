package APITest.tests;

import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import classes.AllUsers;
import classes.TestAPI;
import classes.User;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;;

public class UsersTestGetUsers {

	//Data fields
	private RequestSpecification httpRequest;
	private Response response;
	private JsonPath jsonPath;
	private List<User> allUsers;

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
	@Test(priority=2, dependsOnMethods = {"GetRestAssured"})
	public void GetUsersStatusCode() {					

		//System.out.println("Test Response Status Code for: GET /api/users"); //For debug purposes only

		//RestAssured class is returning the Request against the base URI
		response = httpRequest.request(Method.GET, TestAPI.USERS);

		//int statusCode = response.getStatusCode(); //Get response code
		String statusLine = String.format("%s", response.getStatusLine()); //Get response line

		//Display response code and line:
		//System.out.println("Response Code is =>  " + statusCode); //For debug purposes only
		//System.out.println("Response Line is =>  " + statusLine); //For debug purposes only

		//Test response code
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK", "Correct status code returned");				
	}


	// Test Response Header for: GET /api/users
	// Expected result: application/json; charset=utf-8
	@Test(priority=3, dependsOnMethods = {"GetUsersStatusCode"})
	public void GetResponseHeader() {

		//System.out.println("Test Response Header for: GET /api/users");				 			 								

		// Reader header of a give name. 
		// In this line we will get Header named Content-Type
		String contentType = response.header("Content-Type");
		//System.out.println("Content-Type value: " + contentType); //For debug purposes only

		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}


	//Test Status code for: GET /api/users
	//The expected result: the array of users, total size = 7
	@Test(priority=3, dependsOnMethods = {"GetUsersStatusCode"})
	private void GetResponseBody()
	{
		//System.out.println("Test Response Body for: GET /api/users"); //For debug purposes only

		// Get response body as a string
		//String jsonBody = response.getBody().asString();	

		//Display the string
		//System.out.println("Response Body is =>  " + jsonBody); //For debug purposes only

		// Get the JsonPath object instance from the Response interface
		jsonPath = response.jsonPath();
		List<String> userId = jsonPath.get("id");

		/*
		for(String id : userId)
		{
			System.out.println("User id is =>  " + id); //For debug purposes only
		}
		 */

		//Verify total number of users, expected 7
		Assert.assertEquals(userId.size(), 7);
	}

	//Test Status code for: GET /api/users
	//The expected result: 7 specific users of Type User
	@Test(priority=4, dependsOnMethods = {"GetResponseBody"})
	private void GetAllUsers()
	{
		//System.out.println("Test retreived users for: GET /api/users"); //For debug purposes only

		List<String> userFirstName = jsonPath.get("firstName");
		List<String> userLastName = jsonPath.get("lastName");
		List<Boolean> userIsActive = jsonPath.get("isactive");
		List<String> userId = jsonPath.get("id");
		List<String> userCreated = jsonPath.get("created");
		List<String> userLastLogin = jsonPath.get("lastLogin");
		List<String> userEmail = jsonPath.get("email");

		allUsers = new ArrayList<User>();

		//Generate list of users
		for(int i = 0; i < userId.size(); i++)
		{		
			//String fName, String lName, String newEmail, String newId, boolean newIsActive, String newCreated, long newLastLogin
			allUsers.add(new User(userFirstName.get(i),
					userLastName.get(i),
					userEmail.get(i),
					userId.get(i),
					userIsActive.get(i),
					userCreated.get(i),
					userLastLogin.get(i))
					);
		}
	}

	// Verify that all expected users retrieved from API call
	@Test(priority=5, dependsOnMethods = {"GetAllUsers"})
	private void AssertUsers()
	{
		//System.out.println("Test users: GET /api/users"); //For debug purposes only

		//Instantiate class with test data
		AllUsers testUsers = new AllUsers();

		// Test by list size
		Assert.assertEquals(allUsers.size(), testUsers.getUsers().size()); 

		//Test by fields
		boolean isEqual = false;

		//Compare user by user
		for(User usr : testUsers.getUsers())
		{
			isEqual = false;

			for(User testUsr : allUsers)
			{
				if(testUsr.isSameUser(usr)){

					isEqual = true;
					//System.out.println("Found equal user"); //For debug purposes only
					break;
				}
			}

			//System.out.println("AssertUsers >>> isEqual: " + isEqual); //For debug purposes only
		}

		//Validate if two lists contains equal objects of type User
		Assert.assertTrue(isEqual);
	}


	//End of class
}
