package classes;

import java.util.ArrayList;
import java.util.List;

public class AllUsers {

	private List<User> list = new ArrayList<User>();
	
	public AllUsers(){
		//String fName, String lName, String newEmail, String newId, boolean newIsActive, String newCreated, int newLastLogin
		list.add(new User("Thomas", "Brown", "t.brown@company.com", "thomasbrown", true, "2017-05-14T11:35:10 PST", "1507670749666"));
		list.add(new User("Emily", "Cornwood", "e.cornwood@company.com", "emilycornwood", true, "2017-05-14T11:35:10 PST", "1504670749666"));
		list.add(new User("James", "Hilton", "j.hilton@company.com", "jameshilton", true, "2017-05-14T11:35:10 PST", "1501067444552"));
		list.add(new User("Patricia", "Newton", "p.newton@company.com", "patricianewton", true, "2017-05-14T11:35:10 PST", "1501741752955"));
		list.add(new User("Rick", "Ottoman", "r.ottoman@company.com", "rickottoman", true, "2017-05-14T11:35:10 PST", "1501203269180"));
		list.add(new User("Paul", "Sawyer", "p.sawyer@company.com", "paulsawyer", true, "2017-05-14T11:35:10 PST", "1501662210858"));
		list.add(new User("Alice", "Thompson", "a.thompson@company.com", "alicethompson", true, "2017-05-14T11:35:10 PST", "1501634389808"));
	}
	
	public List<User> getUsers()
	{
		return list;
	}
}
