import java.util.List;

import models.Blog;
import models.Post;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class BlogTest extends UnitTest {
	private User bob, chris;
	private Blog blog1, blog2;

	@BeforeClass
	public static void loadDB() {
		Fixtures.deleteAllModels();
	}

	@Before
	public void setup() {
		bob = new User("bob", "jones", "bob@jones.com", "secret", "1985", "male", "student");
		chris = new User("chris", "downey", "chris@downey.com", "secret", "1980", "male", "student");
		blog1 = new Blog(bob, "Blog1");
		blog2 = new Blog(bob, "Blog2");
		bob.save();
		chris.save();
	}

	@After
	public void teardown() {
		
		bob.delete();
		chris.delete();
	}
	
	@Test
	public void testNoBlogs() {
		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		assertEquals(blogs.size(), 0);
	}
	
	@Test
	public void testCreatePost() {
		bob.blogs.add(blog1);
		bob.save();

		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		assertEquals(1, blogs.size());
		Blog blog = blogs.get(0);
		assertEquals(blog.title, "Blog1");
	}
	
	@Test
	public void testCreateMultiplePosts() {
		bob.blogs.add(blog1);
		bob.blogs.add(blog2);
		bob.save();

		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		assertEquals(2, blogs.size());
		Blog bloga = blogs.get(0);
		assertEquals(bloga.title, "Blog1");

		Blog blogb= blogs.get(1);
		assertEquals(blogb.title, "Blog2");
	}
	
	@Test
	public void testUpdatePost() {
		Blog blog = new Blog(bob,"Post number one");
		assertEquals(blog.title, "Post number one");
		
		blog.title = "New Title";
		
		assertNotSame("Post number one", blog.title);
		assertEquals(blog.title, "New Title");
	}
	
	@Test
	public void testDeletePost() {
		Blog blog3 = new Blog(bob, "This is the third post content");
		blog3.save();
		bob.blogs.add(blog3);
		bob.save();

		User user = User.findByEmail("bob@jones.com");
		assertEquals(1, user.blogs.size());
		Blog blog = user.blogs.get(0);

		user.blogs.remove(0);
		user.save();
		blog.delete();

		User anotherUser = User.findByEmail("bob@jones.com");
		assertEquals(0, anotherUser.blogs.size());
	}
}