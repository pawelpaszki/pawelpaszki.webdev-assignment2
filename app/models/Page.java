package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import play.db.jpa.Model;
@Entity
public class Page extends Model {

	@Lob
	public String pageContent;
	public String pageTitle;
	public String author;

	@OneToMany
	public List<Comment> comments = new ArrayList<Comment>();

	public Page(User author, String pageTitle, String content) {
		this.author = author.firstName + " " + author.lastName;
		this.pageTitle = pageTitle;
		this.pageContent = content;
	}

	public String toString() {
		return pageContent;
	}
}
