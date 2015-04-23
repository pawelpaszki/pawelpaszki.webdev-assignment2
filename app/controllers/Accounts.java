package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Accounts extends Controller {
	public static void signup() {
		render();
	}

	public static void login() {
		render();
	}

	public static void logout() {
		String userId = session.get("logged_in_userid");
		User user = User.findById(Long.parseLong(userId));
		user.online = false;
		user.save();
		session.clear();
		index();
	}

	public static void index() {
		List<User> users = User.findAll();
		render(users);
	}

	public static User getLoggedInUser() {
		User user = null;
		if (session.contains("logged_in_userid")) {
			String userId = session.get("logged_in_userid");
			user = User.findById(Long.parseLong(userId));
		} else {
			login();
		}
		return user;
	}

	public static void register(String firstName, String lastName, String email,
			String password, String dateOfBirth, String sex, String occupation) {
		Logger.info(firstName + " " + lastName + " " + email + " " + password + " " + dateOfBirth + " " + sex + " " + occupation);
		User user = new User(firstName, lastName, email, password, dateOfBirth, sex,
				occupation);
		user.save();
		index();
	}

	public static void authenticate(String email, String password) {
		Logger.info("Attempting to authenticate with " + email + ":" + password);

		User user = User.findByEmail(email);
		if ((user != null) && (user.checkPassword(password) == true)) {
			Logger.info("Authentication successful");
			session.put("logged_in_userid", user.id);
			user.online = true;
			user.save();
			Home.index();
		} else {
			Logger.info("Authentication failed");
			login();
		}
	}
	// to do
	public static void deleteAccount() {
		
	}
}