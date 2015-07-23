package com.p2p.bean.post;

import java.util.Date;


public class Post {

	private int id;
	private String title;
	private String content;
	private String author;
	private Date   time;
	private String slug;
	
	
	public String getSlug() {
		return slug;
	}

	
	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public Date getTime() {
		return time;
	}
	
	public void setTime(Date time) {
		this.time = time;
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	
	
}
