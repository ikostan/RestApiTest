package APITest.tests;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.testng.Assert;
import org.testng.annotations.Test;
import classes.TestAPI;
import classes.User;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UsersTestDataFields {

	//Data fields
	private final String postFix = "@company.com";
	private RequestSpecification httpRequest;
	private Response response;
	private JsonPath jsonPath;
	private List<User> allUsers;
	private final int MIN = 1, MAX = 50;

	// Set up a request
	// Expected result: httpRequest object is not null
	@Test(priority=1)
	public void GetRestAssured() {

		System.out.println("Setting up the base URL to the RESTful: " + TestAPI.URL); //For debug purposes only

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

		//for(String id : userId)
		//{
		//System.out.println("User id is =>  " + id); //For debug purposes only
		//}
		
		boolean notEmpty = false;
		
		if(userId.size() > 0){
			
			notEmpty = true;
		}

		//Verify total number of users > 0
		Assert.assertTrue(notEmpty); 
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


	//Email address validation
	//Expected result "<first letter from first name>.<last name>@company.com", all in lower case
	//Email value must be unique
	@Test(priority=5, dependsOnMethods = {"GetAllUsers"})
	private void AssertEmails()
	{
		//System.out.println("Test emails: GET /api/users"); //For debug purposes only

		//Collection for unique values only
		Set<String> inputSet= new HashSet<String>();

		//Validate email per user
		for(User usr : allUsers)
		{
			//Generate email as expected format based on first name (first leter only), last name and post-fix
			String expectedEmail = String.format("%s.%s%s", 
					Character.toLowerCase(usr.firstName.charAt(0)), 
					usr.lastName.toLowerCase(), 
					postFix);

			//System.out.println("Expected email: " + expectedEmail + ", actual email: " + usr.email); //For debug purposes only

			//Compare between expected and actual formats
			boolean isValid = (usr.email.equals(expectedEmail)) ? true : false;
			//System.out.println("Is email valid: " + isValid); //For debug purposes only

			//Verify if email format is valid
			Assert.assertTrue(isValid);

			boolean isUnique = false;

			if(!inputSet.contains(usr.email))
			{
				isUnique = true;
				inputSet.add(usr.email);
			}

			//Verify is email unique
			Assert.assertTrue(isUnique);
			//System.out.println("Is email unique: " + isUnique); //For debug purposes only

		}
	}


	//Id validation
	//Expected result "<first name><last name>", all in lower case
	@Test(priority=5, dependsOnMethods = {"GetAllUsers"})
	private void AssertIds()
	{
		//System.out.println("Test Ids: GET /api/users"); //For debug purposes only

		//Collection for unique values only
		Set<String> inputSet= new HashSet<String>();

		//Validate email per user
		for(User usr : allUsers)
		{
			//Generate email as expected format based on first name (first leter only), last name and post-fix
			String expectedId = String.format("%s%s", 
					usr.firstName.toLowerCase(), 
					usr.lastName.toLowerCase());

			//System.out.println("Expected id: " + expectedId + ", actual id: " + usr.id); //For debug purposes only

			//Compare between expected and actual formats
			boolean isValid = (usr.id.equals(expectedId)) ? true : false;
			//System.out.println("Is id valid: " + isValid); //For debug purposes only

			//Verify if id format is valid
			Assert.assertTrue(isValid);

			boolean isUnique = false;

			if(!inputSet.contains(usr.id))
			{
				isUnique = true;
				inputSet.add(usr.id);
			}

			//Verify is id unique
			Assert.assertTrue(isUnique);
			//System.out.println("Is id unique: " + isUnique); //For debug purposes only
		}
	}


	//LastLogin validation
	//Expected result "yyyy-MM-dd'T'HH:mm:ss" (from Unix format - milliseconds since epoch (Jan 1 1970)), range between "Dec 31 16:00:00 PST 1969" and NOW
	@Test(priority=5, dependsOnMethods = {"GetAllUsers"})
	private void AssertLastLogin()
	{
		//System.out.println("Test lastLogin: GET /api/users"); //For debug purposes only

		//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
		Date invalidDate = new Date(0);
		Date now = new Date();

		//Validate 'lastLogin' date/time
		for(User usr : allUsers)
		{				
			//Compare between invalid and actual formats
			boolean isValid = (!(usr.lastLogin.equals(invalidDate)) && usr.lastLogin.before(now)) ? true : false;
			//System.out.println("Is 'lastLogin' valid: " + isValid); //For debug purposes only

			//Verify is 'lastLogin' date format valid
			Assert.assertTrue(isValid);
		}
	}


	//DateCreated validation
	//Expected result "yyyy-MM-dd'T'HH:mm:ss" (from Unix format - milliseconds since epoch (Jan 1 1970)), range between "Dec 31 16:00:00 PST 1969" and NOW
	@Test(priority=5, dependsOnMethods = {"GetAllUsers"})
	private void AssertDateCreated()
	{
		//System.out.println("Test 'created' date/time format: GET /api/users"); //For debug purposes only

		//DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
		Date invalidDate = new Date(0);
		Date now = new Date();

		//Validate 'created' date/time
		for(User usr : allUsers)
		{				
			//Compare between invalid and actual formats
			boolean isValid = (!(usr.created.equals(invalidDate)) && usr.created.before(now)) ? true : false;
			//System.out.println("Is 'created' valid: " + isValid); //For debug purposes only

			//Verify is 'created' date format valid
			Assert.assertTrue(isValid);
		}
	}


	//FirstName validation
	//Expected result: min length = 1, max = 50, letters only, first char is capital
	@Test(priority=5, dependsOnMethods = {"GetAllUsers"})
	private void AssertFirstName()
	{
		//System.out.println("Test FirstName format: GET /api/users"); //For debug purposes only

		//Validate 'firstName' format per user
		for(User usr : allUsers)
		{				
			//Validate MIN/MAX length
			boolean isValidLength = (usr.firstName.length() >= MIN && usr.firstName.length() <= MAX) ? true : false;
			//System.out.println("Is 'firstName' has valid length: " + isValidLength); //For debug purposes only

			//Verify is 'firstName' length
			Assert.assertTrue(isValidLength);

			//Validate first letter
			boolean isValidLetter = (Character.isUpperCase(usr.firstName.charAt(0))) ? true : false;
			//System.out.println("Is 'firstName' has valid first char: " + isValidLetter); //For debug purposes only

			//Verify is 'firstName' first letter
			Assert.assertTrue(isValidLetter);

			//Validate first letter
			boolean isLettersOnly = true;

			for(Character chr : usr.firstName.toCharArray())
			{
				if(!Character.isLetter(chr))
				{
					isLettersOnly = false;
					//System.out.println("Char is not a letter: " + chr); //For debug purposes only
					break;
				}
			}

			//System.out.println("Is 'firstName' contains letters only: " + isLettersOnly ); //For debug purposes only

			//Verify is 'firstName' first letter
			Assert.assertTrue(isLettersOnly );
		}
	}


	//LastName validation
	//Expected result: min length = 1, max = 50, letters only, first char is capital
	@Test(priority=5, dependsOnMethods = {"GetAllUsers"})
	private void AssertLastName()
	{
		//System.out.println("Test LastName format: GET /api/users"); //For debug purposes only

		//Validate 'firstName' format per user
		for(User usr : allUsers)
		{				
			//Validate MIN/MAX length
			boolean isValidLength = (usr.lastName.length() >= MIN && usr.lastName.length() <= MAX) ? true : false;
			//System.out.println("Is 'lastName' has valid length: " + isValidLength); //For debug purposes only

			//Verify is 'lastName' length
			Assert.assertTrue(isValidLength);

			//Validate first letter
			boolean isValidLetter = (Character.isUpperCase(usr.lastName.charAt(0))) ? true : false;
			//System.out.println("Is 'lastName' has valid first char: " + isValidLetter); //For debug purposes only

			//Verify is 'firstName' first letter
			Assert.assertTrue(isValidLetter);

			//Validate first letter
			boolean isLettersOnly = true;

			for(Character chr : usr.lastName.toCharArray())
			{
				if(!Character.isLetter(chr))
				{
					isLettersOnly = false;
					//System.out.println("Char is not a letter: " + chr); //For debug purposes only
					break;
				}
			}

			//System.out.println("Is 'lastName' contains letters only: " + isLettersOnly ); //For debug purposes only

			//Verify is 'lastName' first letter
			Assert.assertTrue(isLettersOnly );
		}
	}

	//End of class
}
