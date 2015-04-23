package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class EditProfile extends Controller {
	public static void change(String firstName, String lastName, String email,
			String password, String occupation) {
		User user = Accounts.getLoggedInUser();
		user.firstName = firstName;
		user.lastName = lastName;
		user.email = email;
		user.password = password;
		user.occupation = occupation;
		user.save();
		Home.index();
	}

	public static void index() {
		User user = Accounts.getLoggedInUser();
		render(user);
	}
}