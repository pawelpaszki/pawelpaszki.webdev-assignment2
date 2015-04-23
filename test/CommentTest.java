import java.util.List;

import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class CommentTest extends UnitTest {

	private User bob, chris;
	private Blog blog1;
	private Post post1;
	private Comment comment1, comment2;
	private Page page1;
	
	@BeforeClass
	public static void loadDB() {
		Fixtures.deleteAllModels();
	}

	@Before
	public void setup() {
		bob = new User("bob", "jones", "bob@jones.com", "secret", "1985", "male", "student");
		chris = new User("chris", "downey", "chris@downey.com", "secret", "1980", "male", "student");
		blog1 = new Blog(bob, "Blog1");
		post1 = new Post(bob, "Post title", "This is the first post content");
		page1 = new Page(bob, "Page1 content", "Page1 title");
		bob.save();
		chris.save();
		comment1 = new Comment(bob, "first comment");
		comment2 = new Comment(bob, "second comment");
		
	}
	
	@After
	public void teardown() {
		bob.delete();
		chris.delete();
	}
	
	@Test
	public void testFindComments() {
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).posts.add(post1);
		List<Comment> comments = user.blogs.get(0).posts.get(0).comments;
		comments.add(comment1);
		assertNotNull(comments);
		assertEquals(comments.size() ,1);
	}
	
	@Test
	public void testNoComments(){
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).posts.add(post1);
		List<Comment> comments = user.blogs.get(0).posts.get(0).comments;
		assertEquals(comments.size() ,0);
	}
	
	@Test
	public void testAddSingleComment() {
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).posts.add(post1);
		List<Comment> comments = user.blogs.get(0).posts.get(0).comments;
		comments.add(comment2);
		assertEquals(comments.size(), 1);
		assertEquals(comments.get(0).content, "second comment");
		assertEquals(comments.get(0).commentAuthor, bob.firstName + " " + bob.lastName);		
	}
	
	@Test
	public void testAddMultipleComments() {
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).posts.add(post1);
		List<Comment> comments = user.blogs.get(0).posts.get(0).comments;
		comments.add(comment2);
		comments.add(comment1);
		assertEquals(comments.size(), 2);
		assertEquals(comments.get(0).content, "second comment");
		assertEquals(comments.get(0).commentAuthor, bob.firstName + " " + bob.lastName);	
		assertEquals(comments.get(1).content, "first comment");
		assertEquals(comments.get(1).commentAuthor, bob.firstName + " " + bob.lastName);	
	}
	
	@Test
	public void testNoCommentsOnPage(){
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).posts.add(post1);
		List<Comment> comments = user.blogs.get(0).posts.get(0).comments;
		assertEquals(comments.size() ,0);
	}
	
	@Test
	public void testAddSingleCommentToPage() {
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).pages.add(page1);
		List<Comment> comments = user.blogs.get(0).pages.get(0).comments;
		comments.add(comment2);
		assertEquals(comments.size(), 1);
		assertEquals(comments.get(0).content, "second comment");
		assertEquals(comments.get(0).commentAuthor, bob.firstName + " " + bob.lastName);	
	}
	
	@Test
	public void testAddMultipleCommentsToPage() {
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).pages.add(page1);
		List<Comment> comments = user.blogs.get(0).pages.get(0).comments;
		comments.add(comment2);
		comments.add(comment1);
		assertEquals(comments.size(), 2);
		assertEquals(comments.get(0).content, "second comment");
		assertEquals(comments.get(0).commentAuthor, bob.firstName + " " + bob.lastName);
		assertEquals(comments.get(1).content, "first comment");
		assertEquals(comments.get(1).commentAuthor, bob.firstName + " " + bob.lastName);
	}
	
	@Test
	public void testDeleteComment() {
		User user = User.findByEmail("chris@downey.com");
		user.blogs.add(blog1);
		user.blogs.get(0).posts.add(post1);
		List<Comment> comments = user.blogs.get(0).posts.get(0).comments;
		comments.add(comment2);
		comments.add(comment1);
		assertEquals(comments.size(), 2);
		assertEquals(comments.get(0).content, "second comment");
		assertEquals(comments.get(0).commentAuthor, bob.firstName + " " + bob.lastName);
		assertEquals(comments.get(1).content, "first comment");
		assertEquals(comments.get(1).commentAuthor, bob.firstName + " " + bob.lastName);
		comments.remove(1);
		assertEquals(comments.size(), 1);
		comments.remove(0);
		assertEquals(comments.size(), 0);
	}
	@Test
	public void testUpdateComment() {
		Comment commenta = new Comment(bob, "comment a");
		Comment commentb = new Comment(chris, "comment b");
		
		assertEquals (commenta.content, "comment a");
		assertEquals (commenta.commentAuthor, "bob jones");
		assertEquals (commentb.content, "comment b");
		assertEquals (commentb.commentAuthor, "chris downey");
		commenta.content = "updated content for a";
		commentb.content = "updated content for b";
		assertNotSame(commenta.content, "comment a");
		assertNotSame(commentb.content, "comment b");
		assertNotSame (commenta.commentAuthor, "bob jones");
		assertNotSame (commenta.commentAuthor, "chris downey");
		assertEquals (commenta.content, "updated content for a");
		assertEquals (commentb.content, "updated content for b");
	}
	
}
