package models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Comment extends Model {
	@Lob
	public String content;
	public String commentAuthor;
	public Date postedAt;
	public long authorId;
	public boolean isSpam;

	public Comment(User commentAuthor, String content) {
		this.commentAuthor = commentAuthor.firstName + " " + commentAuthor.lastName;
		this.content = content;
		this.postedAt = new Date();
		this.authorId = commentAuthor.id;
		this.isSpam = false;
	}
	
	public String getDate() {
	    SimpleDateFormat ft = 
	    new SimpleDateFormat ("E yyyy.MM.dd 'at' HH:mm:ss");
	    String date = ft.format(postedAt);
	    return date;
	}
	
}