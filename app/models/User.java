	package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name="`User`") //This is necessary because User is a reserved word in PostGreSQL
public class User extends Model {
	public static boolean loggedIn;
	public String firstName;
	public String lastName;
	public String email;
	public String password;
	public String dateOfBirth;
	public String sex;
	public String occupation;
	public boolean online;
	@OneToMany (mappedBy ="blogger", cascade = CascadeType.ALL)
	public List<Blog> blogs = new ArrayList<Blog>();
	
	public User(String firstName, String lastName, String email,
			String password, String dateOfBirth, String sex, String occupation) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.sex = sex;
		this.occupation = occupation;
	}

	public static User findByEmail(String email) {
		return find("email", email).first();
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public boolean havePublicBlogs() {
		int count = 0;
		for (Blog blog: blogs) {
			if (!blog.isPrivate) {
				count++;
			}
		}
		return(count > 0);
	}
	
	public boolean allBlogsWithAdultContent() {
		int count = 0;
		for (Blog blog: blogs) {
			if (!blog.adultContent) {
				count++;
			}
		}
		return(count == 0);
	}
	
	public boolean isAdult() {
		String temp = dateOfBirth.substring(dateOfBirth.length()-4);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int year = Integer.parseInt(temp);
		return((currentYear-year) >= 18);
	}

}