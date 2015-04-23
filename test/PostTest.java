import java.util.List;

import models.Blog;
import models.Comment;
import models.Post;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class PostTest extends UnitTest {

	private User bob, chris;
	private Blog blog1;
	private Post post1, post2;

	@BeforeClass
	public static void loadDB() {
		Fixtures.deleteAllModels();
	}
	
	@Before
	public void setup() {
		bob = new User("bob", "jones", "bob@jones.com", "secret", "1985", "male", "student");
		chris = new User("chris", "downey", "chris@downey.com", "secret", "1980", "male", "student");
		blog1 = new Blog(bob, "Blog1");
		post1 = new Post(bob, "Post title1", "This is the first post content");
		post2 = new Post(bob, "Post title2", "This is the second post content");
		bob.save();
		chris.save();
	}
	
	@After
	public void teardown() {
		bob.delete();
		chris.delete();
	}
	
	@Test
	public void testNoPosts() {
		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		assertEquals(blogs.get(0).posts.size(), 0);
	}
	
	@Test
	public void testFindPosts() {
		User user = User.findByEmail("chris@downey.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		Blog blog = blogs.get(0);
		List<Post> posts = blog.posts;
		posts.add(post1);
		assertNotNull(posts);
	}
	
	@Test
	public void testAddSinglePost() {
		chris.blogs.add(blog1);
		
		User user = User.findByEmail("chris@downey.com");
		List<Blog> blogs = user.blogs;
		Blog blog = blogs.get(0);
		blog.posts.add(post1);
		
		assertEquals(blog.posts.get(0).postContent, "This is the first post content");
		assertEquals(blog.posts.get(0).author, "bob jones");
	}
	
	@Test
	public void testAddMultiplePosts() {
		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		blogs.get(0).posts.add(post1);
		blogs.get(0).posts.add(post2);
		assertEquals(blogs.get(0).posts.get(0).postContent, "This is the first post content");
		assertEquals(blogs.get(0).posts.get(0).author, "bob jones");
		assertEquals(blogs.get(0).posts.get(1).postContent, "This is the second post content");
		assertEquals(blogs.get(0).posts.get(1).author, "bob jones");
	}
	
	@Test
	public void testDeletePost() {
		User user = User.findByEmail("bob@jones.com");
		List<Blog> blogs = user.blogs;
		blogs.add(blog1);
		blogs.get(0).posts.add(post1);
		assertEquals(blogs.get(0).posts.size(), 1);
		blogs.get(0).posts.remove(0);
		assertEquals(blogs.get(0).posts.size(), 0);
	}
	@Test
	public void testUpdatePost() {
		post1 = new Post(chris, "title", "first post");
		assertEquals (post1.postContent, "first post");
		assertEquals (post1.author, "chris downey");
		assertNotSame(post1.postContent, "This is the first post content");
		assertNotSame(post1.author, "bob jones");
	}
}
