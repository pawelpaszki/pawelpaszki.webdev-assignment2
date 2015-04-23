package controllers;

import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.util.*;

import models.*;

public class Home extends Controller {
	public static void index() {
		User me = Accounts.getLoggedInUser();
		List<User> users = User.findAll();
		render(me, users);
	}
	
	public static void demo() {
		User me = Accounts.getLoggedInUser();
		render(me);
	}
}