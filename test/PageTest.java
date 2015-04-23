
import java.util.List;

import models.Blog;
import models.Page;
import models.Post;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class PageTest extends UnitTest {

	private User bob, chris;
	private Blog blog1;
	private Page page1, page2;

	@BeforeClass
	public static void loadDB() {
		Fixtures.deleteAllModels();
	}
	
	@Before
	public void setup() {
		bob = new User("bob", "jones", "bob@jones.com", "secret", "1985", "male", "student");
		chris = new User("chris", "downey", "chris@downey.com", "secret", "1980", "male", "student");
		blog1 = new Blog(bob, "Blog1");
		page1 = new Page(bob, "Title 1", "This is the first page");
		page2 = new Page(bob, "Title 2", "This is the second page");
		bob.save();
		chris.save();
	}
	
	@After
	public void teardown() {
		bob.delete();
		chris.delete();
	}
	
	@Test
	public void testNoPages() {
		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		assertEquals(blogs.get(0).pages.size(), 0);
	}
	
	@Test
	public void testFindPages() {
		User user = User.findByEmail("chris@downey.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		Blog blog = blogs.get(0);
		List<Page> pages = blog.pages;
		pages.add(page1);
		assertNotNull(pages);
	}
	
	@Test
	public void testAddSinglePage() {
		chris.blogs.add(blog1);
		
		User user = User.findByEmail("chris@downey.com");
		List<Blog> blogs = user.blogs;
		Blog blog = blogs.get(0);
		blog.pages.add(page1);
		
		assertEquals(blog.pages.get(0).pageContent, "This is the first page");
		assertEquals(blog.pages.get(0).author, bob.firstName + " " + bob.lastName);
	}
	
	@Test
	public void testAddMultiplePages() {
		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		blogs.get(0).pages.add(page1);
		blogs.get(0).pages.add(page2);
		assertEquals(blogs.get(0).pages.get(0).pageContent, "This is the first page");
		assertEquals(blogs.get(0).pages.get(0).author, bob.firstName + " " + bob.lastName);
		assertEquals(blogs.get(0).pages.get(1).pageContent, "This is the second page");
		assertEquals(blogs.get(0).pages.get(1).author, bob.firstName + " " + bob.lastName);
	}
	
	@Test
	public void testDeletePage() {
		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		blogs.get(0).pages.add(page1);
		assertEquals(blogs.get(0).pages.size(), 1);
		blogs.get(0).pages.remove(0);
		assertEquals(blogs.get(0).pages.size(), 0);
	}
	@Test
	public void testUpdatePage() {
		Page pagea = new Page(chris, "Title a","first page");
		assertEquals (pagea.pageContent, "first page");
		assertEquals (pagea.author, chris.firstName + " " + chris.lastName);
		assertNotSame(pagea.pageContent, "This is the first page");
		assertNotSame(pagea.author, bob);
		pagea.author = bob.firstName + " " + bob.lastName;
		pagea.pageContent = "content";
		assertEquals (pagea.pageContent, "content");
		assertEquals (pagea.author, bob.firstName + " " + bob.lastName);
		assertNotSame(pagea.pageContent, "first page");
		assertNotSame(pagea.author, chris.firstName + " " + chris.lastName);
	}
}
