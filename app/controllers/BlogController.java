package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.User;
import play.Logger;
import play.mvc.Controller;

public class BlogController extends Controller {
	public static void index() {
		User user = Accounts.getLoggedInUser();
		render(user);
	}

	public static void newblog(String title) {
		User user = Accounts.getLoggedInUser();

		Blog blog = new Blog(user, title);
		blog.save();
		user.blogs.add(0, blog);
		user.save();

		Logger.info("title:" + title);
		Home.index();
	}

	public static void deleteblog(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		Logger.info("Request to delete title:" + blog.title);

		me.blogs.remove(blog);
		blog.delete();
		me.save();
		Home.index();
	}

	public static void viewblog(Long blogid) {
		User me = Accounts.getLoggedInUser();

		Blog blog = Blog.findById(blogid);
		(blog.ownViews)++;
		blog.save();
		render(me, blog);
	}

	public static void postcomment(Long postid, String content) {
		User me = Accounts.getLoggedInUser();
		Post post = Post.findById(postid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(post)) {
				current = blog;
			}
		}
		Comment comment = new Comment(me, content);
		comment.save();
		post.comments.add(0, comment);
		post.save();
		current.save();
		me.save();
		viewblog(current.id);
	}

	public static void deletecomment(Long commentid) {
		User me = Accounts.getLoggedInUser();
		Comment comment = Comment.findById(commentid);
		Post thisPost = null;
		Blog current = null;
		for (Blog blog : me.blogs) {
			for (Post post : blog.posts) {
				if (post.comments.contains(comment)) {
					thisPost = post;
				}
			}
		}
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(thisPost)) {
				current = blog;
			}
		}
		thisPost.comments.remove(comment);
		thisPost.save();
		comment.delete();
		current.save();
		me.save();
		viewblog(current.id);
	}

	public static void viewuserpublicblogs(Long userid) {
		User me = Accounts.getLoggedInUser();
		User user = User.findById(userid);
		render(me, user);
	}

	public static void viewpublicblog(Long blogid) {
		User me = Accounts.getLoggedInUser();

		Blog blog = Blog.findById(blogid);
		List<User> users = User.findAll();
		User user = null;
		for (User aUser : users) {
			if (aUser.blogs.contains(blog)) {
				user = aUser;
			}
		}
		(blog.othersViews)++;
		blog.save();
		render(me, user, blog);
	}

	public static void viewuserpage(Long pageid) {
		User me = Accounts.getLoggedInUser();
		Page page = Page.findById(pageid);
		List<User> users = User.findAll();
		User user = null;
		Blog userBlog = null;
		for (User aUser : users) {
			for (Blog blog : aUser.blogs) {
				if (blog.pages.contains(page)) {
					userBlog = blog;
					user = aUser;
				}
			}
		}
		render(me, user, userBlog, page);
	}

	public static void postcommentpublic(Long postid, String content) {
		User me = Accounts.getLoggedInUser();
		Post post = Post.findById(postid);
		Blog current = null;
		List<User> users = User.findAll();
		for (User user : users)
			for (Blog blog : user.blogs) {
				if (blog.posts.contains(post)) {
					current = blog;
				}
			}
		Comment comment = new Comment(me, content);
		comment.save();
		post.comments.add(0, comment);
		post.save();
		current.save();
		me.save();
		viewpublicblog(current.id);
	}
	
	public static void postcommentpublicpage(Long pageid, String content) {
		User me = Accounts.getLoggedInUser();
		Page page = Page.findById(pageid);
		Blog current = null;
		List<User> users = User.findAll();
		User blogOwner = null;
		for (User user : users)
			for (Blog blog : user.blogs) {
				if (blog.pages.contains(page)) {
					current = blog;
					blogOwner = user;
				}
			}
		Comment comment = new Comment(me, content);
		comment.save();
		page.comments.add(0, comment);
		page.save();
		current.save();
		blogOwner.save();
		viewuserpage(page.id);
	}

}