package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Blog;
import models.Comment;
import models.Page;
import models.Post;
import models.User;
import play.Logger;
import play.db.jpa.JPABase;
import play.mvc.Controller;

public class DashBoard extends Controller {

	public static void settings(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		render(me, blog);
	}

	public static void stats(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		render(me, blog);
	}

	public static void newPost(Long blogid, String postTitle, String postContent) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		Post post = new Post(me, postTitle, postContent);
		post.save();
		blog.posts.add(0, post);
		blog.save();
		me.save();
		Logger.info("post: " + post + " size:" + blog.posts.size());
		viewPosts(blogid);
	}

	public static void createPost(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		render(me, blog);
	}

	public static void viewPosts(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		render(me, blog);
	}

	public static void viewPost(Long postid) {
		User me = Accounts.getLoggedInUser();
		Post post = Post.findById(postid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(post)) {
				current = blog;
			}
		}
		Blog blog = current;
		(blog.ownViews)++;
		blog.save();
		render(me, blog, post);
	}

	public static void deletePost(Long postid) {
		User me = Accounts.getLoggedInUser();
		Post post = Post.findById(postid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(post)) {
				current = blog;
				current.posts.remove(post);
			}
		}
		current.save();
		post.delete();
		me.save();
		Blog blog = current;
		viewPosts(blog.id);
	}

	public static void editPost(Long postid) {
		User me = Accounts.getLoggedInUser();
		Post post = Post.findById(postid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(post)) {
				current = blog;
			}
		}
		Blog blog = current;
		render(me, blog, post);
	}

	public static void updatePost(Long postid, String postTitle,
			String postContent) {
		User me = Accounts.getLoggedInUser();
		Post post = Post.findById(postid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(post)) {
				current = blog;
			}
		}
		post.postTitle = postTitle;
		post.postContent = postContent;
		post.save();
		current.save();
		me.save();
		Blog blog = current;
		viewPosts(blog.id);
	}

	public static void newPage(Long blogid, String pageTitle, String pageContent) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		Page page = new Page(me, pageTitle, pageContent);
		page.save();
		blog.pages.add(0, page);
		blog.save();
		me.save();
		Logger.info("post: " + page + " size:" + blog.pages.size());
		viewPages(blogid);
	}

	public static void createPage(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		render(me, blog);
	}

	public static void viewPages(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		render(me, blog);
	}

	public static void viewPage(Long pageid) {
		User me = Accounts.getLoggedInUser();
		Page page = Page.findById(pageid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(page)) {
				current = blog;
			}
		}
		Blog blog = current;
		(blog.ownViews)++;
		blog.save();
		render(me, blog, page);
	}

	public static void deletePage(Long pageid) {
		User me = Accounts.getLoggedInUser();
		Page page = Page.findById(pageid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(page)) {
				current = blog;
				current.pages.remove(page);
			}
		}
		current.save();
		page.delete();
		me.save();
		viewPages(current.id);
	}

	public static void editPage(Long pageid) {
		User me = Accounts.getLoggedInUser();
		Page page = Page.findById(pageid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(page)) {
				current = blog;
			}
		}
		Blog blog = current;
		render(me, blog, page);
	}

	public static void updatePage(Long pageid, String pageTitle,
			String pageContent) {
		User me = Accounts.getLoggedInUser();
		Page page = Page.findById(pageid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(page)) {
				current = blog;
			}
		}
		page.pageTitle = pageTitle;
		page.pageContent = pageContent;
		page.save();
		current.save();
		me.save();
		Blog blog = current;
		viewPages(blog.id);
	}

	public static void postComment(Long postid, String content) {
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
		viewPost(post.id);
	}

	public static void deleteComment(Long commentid) {
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

		current.save();
		me.save();
		viewPost(thisPost.id);
	}

	public static void commentsDeleteComment(Long commentid) {
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

		current.save();
		me.save();
		viewComments(current.id);
	}

	public static void spamDeleteComment(Long commentid) {
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

		current.save();
		me.save();
		viewSpam(current.id);
	}

	public static void postCommentToPage(Long pageid, String content) {
		User me = Accounts.getLoggedInUser();
		Page page = Page.findById(pageid);
		Blog current = null;
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(page)) {
				current = blog;
			}
		}
		Comment comment = new Comment(me, content);
		comment.save();
		page.comments.add(0, comment);
		page.save();
		current.save();
		me.save();
		viewPage(page.id);
	}

	public static void deletePageComment(Long commentid) {
		User me = Accounts.getLoggedInUser();
		Comment comment = Comment.findById(commentid);
		Page thisPage = null;
		Blog current = null;
		for (Blog blog : me.blogs) {
			for (Page page : blog.pages) {
				if (page.comments.contains(comment)) {
					thisPage = page;
				}
			}
		}
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(thisPage)) {
				current = blog;
			}
		}
		thisPage.comments.remove(comment);
		thisPage.save();

		current.save();
		me.save();
		viewPage(thisPage.id);
	}

	public static void markAsNotSpam(Long commentid) {
		User me = Accounts.getLoggedInUser();
		Comment comment = Comment.findById(commentid);
		comment.isSpam = false;
		comment.save();
		Post thisPost = null;
		Page thisPage = null;
		Blog current = null;
		for (Blog blog : me.blogs) {
			for (Post post : blog.posts) {
				if (post.comments.contains(comment)) {
					thisPost = post;
					thisPost.save();
				}
			}
		}
		for (Blog blog : me.blogs) {
			for (Page page : blog.pages) {
				if (page.comments.contains(comment)) {
					thisPage = page;
					thisPage.save();
				}
			}
		}
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(thisPost)) {
				current = blog;
			}
		}
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(thisPage)) {
				current = blog;
			}
		}

		current.save();
		me.save();
		viewSpam(current.id);
	}

	public static void markAsSpam(Long commentid) {
		User me = Accounts.getLoggedInUser();
		Comment comment = Comment.findById(commentid);
		comment.isSpam = true;
		comment.save();
		Post thisPost = null;
		Page thisPage = null;
		Blog current = null;
		for (Blog blog : me.blogs) {
			for (Post post : blog.posts) {
				if (post.comments.contains(comment)) {
					thisPost = post;
					thisPost.save();
				}
			}
		}
		for (Blog blog : me.blogs) {
			for (Page page : blog.pages) {
				if (page.comments.contains(comment)) {
					thisPage = page;
					thisPage.save();
				}
			}
		}
		for (Blog blog : me.blogs) {
			if (blog.posts.contains(thisPost)) {
				current = blog;
			}
		}
		for (Blog blog : me.blogs) {
			if (blog.pages.contains(thisPage)) {
				current = blog;
			}
		}


		current.save();
		me.save();
		viewComments(current.id);
	}

	public static void viewComments(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		ArrayList<Comment> comments = new ArrayList<Comment>();
		ArrayList<Post> posts = new ArrayList<Post>();
		ArrayList<Page> pages = new ArrayList<Page>();
		for (Post post : blog.posts) {
			posts.add(post);
			for (Comment comment : post.comments) {
				comments.add(comment);
			}
		}
		for (Page page : blog.pages) {
			pages.add(page);
			for (Comment comment : page.comments) {
				comments.add(comment);
			}
		}

		render(me, blog, comments, posts, pages);
	}

	public static void viewSpam(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		ArrayList<Comment> comments = new ArrayList<Comment>();
		ArrayList<Post> posts = new ArrayList<Post>();
		ArrayList<Page> pages = new ArrayList<Page>();
		for (Post post : blog.posts) {
			posts.add(post);
			for (Comment comment : post.comments) {
				if (comment.isSpam) {
					comments.add(comment);
				}
			}
		}
		for (Page page : blog.pages) {
			pages.add(page);
			for (Comment comment : page.comments) {
				if (comment.isSpam) {
					comments.add(comment);
				}
			}
		}

		render(me, blog, comments, posts, pages);
	}

	public static void makePrivate(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		blog.isPrivate = true;
		blog.save();
		me.save();
		Logger.info("blog: " + blog.id + " private:" + blog.isPrivate);
		settings(blogid);
	}

	public static void makePublic(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		blog.isPrivate = false;
		blog.save();
		me.save();
		Logger.info("blog: " + blog.id + " private:" + blog.isPrivate);
		settings(blogid);
	}

	public static void dontAllowComments(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		blog.otherCommentsNotAllowed = true;
		blog.save();
		me.save();
		Logger.info("blog: " + blog.id + " comments allowed"
				+ !blog.otherCommentsNotAllowed);
		settings(blogid);
	}

	public static void allowComments(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		blog.otherCommentsNotAllowed = false;
		blog.save();
		me.save();
		Logger.info("blog: " + blog.id + " comments allowed:"
				+ !blog.otherCommentsNotAllowed);
		settings(blogid);
	}

	public static void isAdultContent(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		blog.adultContent = true;
		blog.save();
		me.save();
		Logger.info("blog: " + blog.id + " adult content:" + blog.adultContent);
		settings(blogid);
	}

	public static void notAdultContent(Long blogid) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		blog.adultContent = false;
		blog.save();
		me.save();
		Logger.info("blog: " + blog.id + " adult content:" + blog.adultContent);
		settings(blogid);
	}

	public static void changeBlogTitle(Long blogid, String newTitle) {
		User me = Accounts.getLoggedInUser();
		Blog blog = Blog.findById(blogid);
		blog.title = newTitle;
		blog.save();
		me.save();
		settings(blogid);
	}

}
