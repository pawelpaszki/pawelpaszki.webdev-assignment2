package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Blog extends Model {
	public String title;
	public Date blogPostedAt;
	public Boolean isPrivate;
	@ManyToOne
	public User blogger;	
	public Integer ownViews;
	public Integer othersViews;
	public Boolean otherCommentsNotAllowed;
	public Boolean adultContent;
	public Integer followers;
	@OneToMany//(cascade = CascadeType.ALL)
	public List<Post> posts = new ArrayList<Post>();
	@OneToMany
	public List<Page> pages = new ArrayList<Page>();

	public Blog(User author, String title) {
		this.blogger = author;
		this.title = title;
		this.blogPostedAt = new Date();
		this.isPrivate = false;
		ownViews = 0;
		othersViews = 0;
		otherCommentsNotAllowed = false;
		adultContent = false;
	}

	public String toString() {
		return title;
	}
	
	public String getDate() {
	    SimpleDateFormat ft = 
	    new SimpleDateFormat ("E yyyy.MM.dd 'at' HH:mm:ss");
	    String date = ft.format(blogPostedAt);
	    return date;
	}
	public int commentsCount() {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for (Post post : posts) {
			for (Comment comment : post.comments) {
				comments.add(comment);
			}
		}
		for (Page page : pages) {
			for (Comment comment : page.comments) {
				comments.add(comment);
			}
		}
		return comments.size();
	}
	public int spamCount() {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		for (Post post: posts) {
			for (Comment comment: post.comments) {
				if (comment.isSpam) {
					comments.add(comment);
				}
			}
		}
		for (Page page: pages) {
			for (Comment comment: page.comments) {
				if (comment.isSpam) {
					comments.add(comment);
				}
			}
		}
		return comments.size();
	}
}