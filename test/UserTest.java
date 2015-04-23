import java.util.ArrayList;
import java.util.List;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class UserTest extends UnitTest {
	private User bob, chris;

	@BeforeClass
	public static void loadDB() {
		Fixtures.deleteAllModels();
	}

	@Before
	public void setup() {
		bob = new User("bob", "jones", "bob@jones.com", "secret", "26/06/1985",
				"male", "student");
		chris = new User("chris", "brown", "chris@brown.com", "secret",
				"25/05/1984", "male", "manager");
		bob.save();
		chris.save();
	}

	@After
	public void teardown() {
		bob.delete();
		chris.delete();
	}

	@Test
	public void testCreateUser() {
		User testUser = User.findByEmail("bob@jones.com");
		assertNotNull(testUser);
	}

	@Test
	public void testCreateMultipleUsers() {
		User userOne = User.findByEmail("bob@jones.com");
		User userTwo = User.findByEmail("chris@brown.com");
		assertNotNull(userOne);
		assertNotNull(userTwo);
		List<User> users = new ArrayList<User>();
		users.add(userOne);
		users.add(userTwo);
		assertEquals(users.size(), 2);
		
	}
	
	@Test
	public void deleteUsers() {
		List<User> users = new ArrayList<User>();
		users.add(bob);
		users.add(chris);
		assertEquals(users.size(), 2);
		users.remove(1);
		assertEquals(users.size(),1);
		users.remove(0);
		assertEquals(users.size(), 0);	
	}

	@Test
	public void testFindUser() {
		User alice = User.findByEmail("alice@jones.com");
		assertNull(alice);
	}
	
	@Test
	public void updateUser(){
		User user = User.findByEmail("bob@jones.com");
		user.firstName = "Tom";
		user.lastName = "Jones";
		assertEquals(user.firstName, "Tom");
		assertEquals(user.lastName, "Jones");
	}
}
