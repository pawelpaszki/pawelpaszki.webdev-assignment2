package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class Post extends Model {

	@Lob
	public String postContent;
	public Date postPostedAt;
	public String author;
	public String postTitle;

	@OneToMany
	public List<Comment> comments = new ArrayList<Comment>();

	public Post(User author, String postTitle, String postContent) {
		this.author = author.firstName + " " + author.lastName;
		this.postTitle = postTitle;
		this.postContent = postContent;
		this.postPostedAt = new Date();
	}

	public String toString() {
		return postContent;
	}
	
	public String getDate() {
	    SimpleDateFormat ft = 
	    new SimpleDateFormat ("E yyyy.MM.dd 'at' HH:mm:ss");
	    String date = ft.format(postPostedAt);
	    return date;
	}
}
