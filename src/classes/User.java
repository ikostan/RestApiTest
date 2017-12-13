package classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
	
	//Required data fields
	public String firstName;
	public String lastName;
	public String email;
	public String id; //Required on Update only
	
	//Not mandatory fields (auto-generated)
	public boolean isActive;
	public Date created;
	public Date lastLogin;
	
	//Constructor
	public User(String fName, String lName, String newEmail, String newId){
		
		firstName = fName;	
		lastName = lName;
		email = newEmail;
		id = newId;
	}
	
	//Constructor
	public User(String fName, String lName, String newEmail, String newId, boolean newIsActive, String newCreated, String newLastLogin){
			
		firstName = fName;	
		lastName = lName;
		email = newEmail;
		id = newId;
		isActive = newIsActive;
		created = setDateCreated(newCreated);
		lastLogin = setLastLogin(newLastLogin);
	}
	
	//Test if two objects have exact same fields
	public boolean isSameUser(User u)
	{
		if(u == null)
		{
			return false;
		}
		
		if(!(u instanceof User))
		{
			return false;
		}
		
		return this.firstName.equals(u.firstName) && 
				this.lastName.equals(u.lastName) && 
				this.email.equals(u.email) && 
				this.id.equals(u.id) && 
				this.isActive == u.isActive &&
				this.created.equals(u.created) &&
				this.lastLogin.equals(u.lastLogin);
	}
	
	public Date setLastLogin(String s)
	{		
		try 
		{
			java.util.Date time = new java.util.Date(Long.parseLong(s));
			return time;
		} 
		catch(Exception e)
		{
			System.out.println("Error: wrong date/time format: " + e.getMessage()); //For debug purposes only
			Date date = new Date(0);
			return date;
		}
	}
	
	public Date setDateCreated(String d)
	{
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");  
		
		try {
			
			Date date = formatter.parse(d);
			return date;  
		} 
		catch (ParseException e) 
		{			
			//System.out.println("Error: wrong date/time format: " + e.getMessage()); //For debug purposes only
			Date date = new Date(0);
			return date;
		} 	
	}
	
	//End of class
}
