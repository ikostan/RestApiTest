# Rest Api Test
Testing REST API with TestNG Framework, Eclipse IDE and Java

### Description:
One of the developers implemented an API to manage the users, albeit under severe time crunch. Below is the specification requirements that the developer followed when implementing the API. The objective is to test the APIs and find any problems with it. Please send back to us the following:

### Specification Requirements:
The Users API is intended to manage the users’ accounts in widely used software. Every user has a first and last name, the unique email address and some flags to show on the UI - e.g. if the user is active or when it was created. All required properties are listed in the table below.




### API:

1. GET /api/users<br/>

Retrieves a list of users<br/>

Request Body: none<br/>

Response: the array of users<br/><br/>


2. GET /api/users/:id<br/>

Retrieves a specific user as an object<br/>

Request Body: none<br/>

Response: the matching user object or 404<br/><br/>


3. POST /api/users<br/>

Creates a new user<br/>

Request Body: JSON with first and last names, plus the email<br/>

Response: the newly created user with auto-populated fields<br/><br/>


4. PUT /api/users/:id<br/>

Updates an existing user<br/>

Request Body: JSON with updated user information<br/>

Response: 201 or 404<br/><br/>


5. POST /api/users/reset<br/>
A helper API to reset the data back to the original state. No need to test this<br/>
